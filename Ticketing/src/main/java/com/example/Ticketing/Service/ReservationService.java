package com.example.Ticketing.Service;

import com.example.Ticketing.Exception.BusinessException;
import com.example.Ticketing.Exception.ResourceNotFoundException;
import com.example.Ticketing.Model.DTO.Request.ReservationRequest;
import com.example.Ticketing.Model.DTO.Response.ProductResponse;
import com.example.Ticketing.Model.DTO.Response.ReservationResponse;
import com.example.Ticketing.Model.Entity.Product;
import com.example.Ticketing.Model.Entity.Reservation;
import com.example.Ticketing.Model.Entity.Session;
import com.example.Ticketing.Model.Entity.User;
import com.example.Ticketing.Model.Enum.ReservationStatus;
import com.example.Ticketing.Repository.ProductRepository;
import com.example.Ticketing.Repository.ReservationRepository;
import com.example.Ticketing.Repository.SessionRepository;
import com.example.Ticketing.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private static final Logger log = LoggerFactory.getLogger(ReservationService.class);

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    ProductRepository productRepository;

    public ReservationResponse createReservation(ReservationRequest request) {
        log.info("Starting reservation creation for userId: {}, sessionId: {}", request.getUserId(), request.getSessionId());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> {
                    log.error("User not found: {}", request.getUserId());
                    return new ResourceNotFoundException("User not found");
                });

        Session session = sessionRepository.findById(request.getSessionId())
                .orElseThrow(() -> {
                    log.error("Session not found: {}", request.getSessionId());
                    return new ResourceNotFoundException("Session not found");
                });

        if (session.getAvailableSeats() < request.getSeatCount()) {
            log.warn("Not enough available seats. Requested: {}, Available: {}", request.getSeatCount(), session.getAvailableSeats());
            throw new BusinessException("Not enough available seats");
        }

        List<Product> products = Collections.emptyList();
        if (request.getProductIds() != null && !request.getProductIds().isEmpty()) {
            products = productRepository.findAllById(request.getProductIds());
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setSession(session);
        reservation.setSeatCount(request.getSeatCount());
        reservation.setProducts(products);
        reservation.setExpirationTime(LocalDateTime.now().plusMinutes(15));

        session.setAvailableSeats(session.getAvailableSeats() - request.getSeatCount());

        reservation = reservationRepository.save(reservation);
        sessionRepository.save(session);

        log.info("Reservation created with ID: {}", reservation.getId());
        return convertToResponse(reservation);
    }

    public List<ReservationResponse> getUserReservations(Long userId) {
        log.info("Fetching reservations for user ID: {}", userId);
        return reservationRepository.findByUserId(userId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancelReservation(Long reservationId) {
        log.info("Attempting to cancel reservation ID: {}", reservationId);

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> {
                    log.error("Reservation not found: {}", reservationId);
                    return new ResourceNotFoundException("Reservation not found");
                });

        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            log.warn("Reservation ID {} is not active. Status: {}", reservationId, reservation.getStatus());
            throw new BusinessException("Only active reservations can be cancelled");
        }

        Session session = reservation.getSession();
        session.setAvailableSeats(session.getAvailableSeats() + reservation.getSeatCount());

        reservation.setStatus(ReservationStatus.CANCELLED);

        reservationRepository.save(reservation);
        sessionRepository.save(session);

        log.info("Reservation ID {} cancelled successfully", reservationId);
    }

    private ReservationResponse convertToResponse(Reservation reservation) {
        BigDecimal seatsTotal = reservation.getSession().getSeatPrice()
                .multiply(BigDecimal.valueOf(reservation.getSeatCount()));

        BigDecimal productsTotal = reservation.getProducts().stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPrice = seatsTotal.add(productsTotal);

        log.debug("Converted reservation ID {} to response", reservation.getId());

        return new ReservationResponse(
                reservation.getId(),
                reservation.getUser().getId(),
                reservation.getSession().getId(),
                reservation.getSession().getName(),
                reservation.getSeatCount(),
                reservation.getProducts().stream()
                        .map(p -> new ProductResponse(p.getId(), p.getName(), p.getPrice()))
                        .collect(Collectors.toList()),
                totalPrice,
                reservation.getCreatedAt(),
                reservation.getStatus()
        );
    }

    @Scheduled(fixedRate = 60000) // Executa a cada minuto
    @Transactional
    public void expireReservations() {
        LocalDateTime now = LocalDateTime.now();
        log.info("Running scheduled task to expire reservations at {}", now);

        List<Reservation> expired = reservationRepository
                .findByStatusAndExpirationTimeBefore(ReservationStatus.ACTIVE, now);

        if (expired.isEmpty()) {
            log.info("No expired reservations found");
            return;
        }

        log.info("Found {} expired reservations", expired.size());

        for (Reservation reservation : expired) {
            try {
                Session session = reservation.getSession();
                session.setAvailableSeats(session.getAvailableSeats() + reservation.getSeatCount());
                sessionRepository.save(session);

                reservation.setStatus(ReservationStatus.EXPIRED);

                log.info("Reservation {} expired. Freed {} seats for session {}",
                        reservation.getId(),
                        reservation.getSeatCount(),
                        session.getId());

            } catch (Exception e) {
                log.error("Error expiring reservation {}: {}", reservation.getId(), e.getMessage());
            }
        }

        reservationRepository.saveAll(expired);
        log.info("Expired reservations updated successfully.");
    }
}
