package com.example.Ticketing.Service;

import com.example.Ticketing.Exception.BusinessException;
import com.example.Ticketing.Exception.ResourceNotFoundException;
import com.example.Ticketing.Model.DTO.Request.PaymentRequest;
import com.example.Ticketing.Model.DTO.Response.PaymentResponse;
import com.example.Ticketing.Model.DTO.Response.ProductResponse;
import com.example.Ticketing.Model.Entity.*;
import com.example.Ticketing.Model.Enum.PaymentStatus;
import com.example.Ticketing.Model.Enum.ReservationStatus;
import com.example.Ticketing.Repository.PaymentRepository;
import com.example.Ticketing.Repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    IdempotencyService idempotencyService;

    public PaymentResponse processPayment(PaymentRequest request, String idempotencyKey) {
        log.info("Start processing payment for reservationId: {}, idempotencyKey: {}", request.getReservationId(), idempotencyKey);

        if (paymentRepository.existsByIdempotencyKey(idempotencyKey)) {
            log.warn("Duplicate payment detected with idempotencyKey: {}", idempotencyKey);
            Payment existingPayment = paymentRepository.findByIdempotencyKey(idempotencyKey)
                    .orElseThrow(() -> new BusinessException("Duplicate payment detected"));
            return convertToResponse(existingPayment);
        }

        Reservation reservation = reservationRepository.findById(request.getReservationId())
                .orElseThrow(() -> {
                    log.error("Reservation not found: {}", request.getReservationId());
                    return new ResourceNotFoundException("Reservation not found");
                });

        log.info("Validating reservation status...");
        validateReservation(reservation);

        BigDecimal amount = calculateTotal(reservation);
        log.info("Total calculated: {}", amount);

        String transactionId = processPaymentTransaction(request.getPaymentToken(), amount);
        log.info("Transaction ID generated: {}", transactionId);

        Payment payment = new Payment();
        payment.setReservation(reservation);
        payment.setMethod(request.getMethod());
        payment.setIdempotencyKey(idempotencyKey);
        payment.setTransactionId(transactionId);
        payment.setAmount(amount);
        payment.setStatus(PaymentStatus.COMPLETED);

        payment = paymentRepository.save(payment);
        log.info("Payment saved with ID: {}", payment.getId());

        reservation.setStatus(ReservationStatus.COMPLETED);
        reservationRepository.save(reservation);
        log.info("Reservation {} marked as COMPLETED", reservation.getId());

        return convertToResponse(payment);
    }

    public Page<PaymentResponse> getPaymentsByUser(Long userId, Pageable pageable) {
        log.info("Fetching payments for user ID: {}", userId);
        return paymentRepository.findByUserId(userId, pageable)
                .map(this::convertToResponseUser);
    }

    private void validateReservation(Reservation reservation) {
        if (reservation.getStatus() == ReservationStatus.COMPLETED) {
            log.warn("Reservation already paid. ID: {}", reservation.getId());
            throw new BusinessException("Reservation already paid");
        }

        if (LocalDateTime.now().isAfter(reservation.getExpirationTime())) {
            log.warn("Reservation expired. ID: {}", reservation.getId());
            throw new BusinessException("Reservation expired");
        }

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            log.warn("Reservation cancelled. ID: {}", reservation.getId());
            throw new BusinessException("Reservation cancelled");
        }
    }

    private BigDecimal calculateTotal(Reservation reservation) {
        BigDecimal seatsTotal = reservation.getSession().getSeatPrice()
                .multiply(BigDecimal.valueOf(reservation.getSeatCount()));

        BigDecimal productsTotal = reservation.getProducts().stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return seatsTotal.add(productsTotal);
    }

    private String processPaymentTransaction(String paymentToken, BigDecimal amount) {
        log.info("Processing payment of {} using token: {}", amount, paymentToken);
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8);
    }

    private PaymentResponse convertToResponse(Payment payment) {
        log.debug("Converting Payment entity to DTO. ID: {}", payment.getId());
        return new PaymentResponse(
                payment.getId(),
                payment.getReservation().getId(),
                payment.getMethod(),
                payment.getTransactionId(),
                payment.getAmount(),
                payment.getStatus()
        );
    }

    private PaymentResponse convertToResponseUser(Payment payment) {
        Reservation reservation = payment.getReservation();
        log.debug("Building detailed payment response for reservation ID: {}", reservation.getId());
        return new PaymentResponse(
                payment.getId(),
                reservation.getId(),
                payment.getMethod(),
                payment.getTransactionId(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getCreatedAt(),
                reservation.getProducts().stream()
                        .map(p -> new ProductResponse(p.getId(), p.getName(), p.getPrice()))
                        .collect(Collectors.toList())
        );
    }
}
