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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    ProductRepository productRepository;


    public ReservationResponse createReservation(ReservationRequest request) {
        // Buscar usuário
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Buscar sessão
        Session session = sessionRepository.findById(request.getSessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));

        // Verificar disponibilidade
        if (session.getAvailableSeats() < request.getSeatCount()) {
            throw new BusinessException("Not enough available seats");
        }

        // Buscar produtos (se houver)
        List<Product> products = Collections.emptyList();
        if (request.getProductIds() != null && !request.getProductIds().isEmpty()) {
            products = productRepository.findAllById(request.getProductIds());
        }

        // Criar reserva
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setSession(session);
        reservation.setSeatCount(request.getSeatCount());
        reservation.setProducts(products);

        // Atualizar disponibilidade na sessão
        session.setAvailableSeats(session.getAvailableSeats() - request.getSeatCount());

        reservation = reservationRepository.save(reservation);
        sessionRepository.save(session);

        return convertToResponse(reservation);
    }

    public List<ReservationResponse> getUserReservations(Long userId) {
        return reservationRepository.findByUserId(userId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            throw new BusinessException("Only active reservations can be cancelled");
        }

        // Liberar assentos
        Session session = reservation.getSession();
        session.setAvailableSeats(session.getAvailableSeats() + reservation.getSeatCount());

        // Atualizar status
        reservation.setStatus(ReservationStatus.CANCELLED);

        reservationRepository.save(reservation);
        sessionRepository.save(session);
    }

    private ReservationResponse convertToResponse(Reservation reservation) {
        // Calcular preço total
        BigDecimal seatsTotal = reservation.getSession().getSeatPrice()
                .multiply(BigDecimal.valueOf(reservation.getSeatCount()));

        BigDecimal productsTotal = reservation.getProducts().stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPrice = seatsTotal.add(productsTotal);

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
}

