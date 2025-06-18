package com.example.Ticketing.Service;

import com.example.Ticketing.Model.DTO.Request.ReservationRequest;
import com.example.Ticketing.Model.DTO.Response.ReservationResponse;
import com.example.Ticketing.Model.Entity.Reservation;
import com.example.Ticketing.Model.Entity.Session;
import com.example.Ticketing.Model.Entity.User;
import com.example.Ticketing.Model.Enum.ReservationStatus;
import com.example.Ticketing.Repository.ReservationRepository;
import com.example.Ticketing.Repository.SessionRepository;
import com.example.Ticketing.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserReservations() {
        Reservation reservation = mock(Reservation.class);
        Session session = mock(Session.class);
        when(reservation.getSession()).thenReturn(session);
        when(session.getSeatPrice()).thenReturn(java.math.BigDecimal.ONE);
        when(reservation.getSeatCount()).thenReturn(1);
        when(reservation.getProducts()).thenReturn(Collections.emptyList());
        when(reservation.getId()).thenReturn(1L);
        User user = mock(User.class);
        when(reservation.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(1L);
        when(session.getId()).thenReturn(1L);
        when(session.getName()).thenReturn("Sessão Teste");
        when(reservation.getCreatedAt()).thenReturn(java.time.LocalDateTime.now());
        when(reservation.getStatus()).thenReturn(ReservationStatus.ACTIVE);
        when(reservationRepository.findByUserId(1L)).thenReturn(List.of(reservation));
        List<ReservationResponse> result = reservationService.getUserReservations(1L);
        assertNotNull(result);
        verify(reservationRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testCancelReservation() {
        Reservation reservation = mock(Reservation.class);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservation.getStatus()).thenReturn(ReservationStatus.ACTIVE);
        Session session = mock(Session.class);
        when(reservation.getSession()).thenReturn(session);
        when(reservation.getSeatCount()).thenReturn(1);
        when(session.getAvailableSeats()).thenReturn(10);
        reservationService.cancelReservation(1L);
        verify(reservation, times(1)).setStatus(ReservationStatus.CANCELLED);
        verify(reservationRepository, times(1)).save(reservation);
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    void testCreateReservation() {
        ReservationRequest request = mock(ReservationRequest.class);
        when(request.getUserId()).thenReturn(1L);
        when(request.getSessionId()).thenReturn(2L);
        when(request.getSeatCount()).thenReturn(2);
        when(request.getProductIds()).thenReturn(Collections.emptyList());
        User user = mock(User.class);
        Session session = mock(Session.class);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(sessionRepository.findById(2L)).thenReturn(Optional.of(session));
        when(session.getAvailableSeats()).thenReturn(5);
        Reservation reservation = mock(Reservation.class);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        when(sessionRepository.save(any(Session.class))).thenReturn(session);
        // Mock para convertToResponse
        when(reservation.getSession()).thenReturn(session);
        when(reservation.getSeatCount()).thenReturn(2);
        when(reservation.getProducts()).thenReturn(Collections.emptyList());
        when(reservation.getId()).thenReturn(10L);
        when(reservation.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(1L);
        when(session.getId()).thenReturn(2L);
        when(session.getName()).thenReturn("Sessão Teste");
        when(session.getSeatPrice()).thenReturn(java.math.BigDecimal.ONE);
        when(reservation.getCreatedAt()).thenReturn(LocalDateTime.now());
        when(reservation.getStatus()).thenReturn(ReservationStatus.ACTIVE);
        ReservationResponse response = reservationService.createReservation(request);
        assertNotNull(response);
        verify(userRepository, times(1)).findById(1L);
        verify(sessionRepository, times(1)).findById(2L);
        verify(reservationRepository, times(1)).save(any(Reservation.class));
        verify(sessionRepository, times(1)).save(any(Session.class));
    }

    @Test
    void testExpireReservations() {
        Reservation reservation = mock(Reservation.class);
        Session session = mock(Session.class);
        when(reservation.getSession()).thenReturn(session);
        when(reservation.getSeatCount()).thenReturn(2);
        when(session.getAvailableSeats()).thenReturn(5);
        List<Reservation> expired = List.of(reservation);
        when(reservationRepository.findByStatusAndExpirationTimeBefore(eq(ReservationStatus.ACTIVE), any(LocalDateTime.class))).thenReturn(expired);
        reservationService.expireReservations();
        verify(reservation, times(1)).setStatus(ReservationStatus.EXPIRED);
        verify(sessionRepository, times(1)).save(session);
        verify(reservationRepository, times(1)).saveAll(expired);
    }
}
