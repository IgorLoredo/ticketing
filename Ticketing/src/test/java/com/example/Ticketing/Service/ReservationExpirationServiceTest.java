package com.example.Ticketing.Service;

import com.example.Ticketing.Model.Entity.Reservation;
import com.example.Ticketing.Model.Entity.Session;
import com.example.Ticketing.Model.Enum.ReservationStatus;
import com.example.Ticketing.Repository.ReservationRepository;
import com.example.Ticketing.Repository.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;

class ReservationExpirationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private SessionRepository sessionRepository;
    @InjectMocks
    private ReservationExpirationService expirationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExpireReservations() {
        Reservation r1 = mock(Reservation.class);
        Reservation r2 = mock(Reservation.class);
        Session s1 = mock(Session.class);
        Session s2 = mock(Session.class);
        when(r1.getSession()).thenReturn(s1);
        when(r2.getSession()).thenReturn(s2);
        when(r1.getSeatCount()).thenReturn(2);
        when(r2.getSeatCount()).thenReturn(3);
        when(s1.getAvailableSeats()).thenReturn(10);
        when(s2.getAvailableSeats()).thenReturn(20);
        List<Reservation> expired = Arrays.asList(r1, r2);
        when(reservationRepository.findByStatusAndExpirationTimeBefore(eq(ReservationStatus.ACTIVE), any(LocalDateTime.class))).thenReturn(expired);
        expirationService.expireReservations();
        verify(r1, times(1)).setStatus(ReservationStatus.EXPIRED);
        verify(r2, times(1)).setStatus(ReservationStatus.EXPIRED);
        verify(sessionRepository, times(1)).save(s1);
        verify(sessionRepository, times(1)).save(s2);
        verify(reservationRepository, times(1)).saveAll(expired);
    }
}
