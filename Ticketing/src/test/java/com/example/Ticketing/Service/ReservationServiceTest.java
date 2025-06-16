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
        when(reservationRepository.findByUserId(1L)).thenReturn(List.of(reservation));
        List<ReservationResponse> result = reservationService.getUserReservations(1L);
        assertNotNull(result);
        verify(reservationRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testCancelReservation() {
        Reservation reservation = mock(Reservation.class);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        reservationService.cancelReservation(1L);
        verify(reservation, times(1)).setStatus(ReservationStatus.CANCELLED);
        verify(reservationRepository, times(1)).save(reservation);
    }
}
