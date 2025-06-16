package com.example.Ticketing.Service;

import com.example.Ticketing.Model.Entity.Reservation;
import com.example.Ticketing.Model.Enum.ReservationStatus;
import com.example.Ticketing.Repository.ReservationRepository;
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
        List<Reservation> expired = Arrays.asList(r1, r2);
        when(reservationRepository.findExpiredReservations(any(LocalDateTime.class), eq(ReservationStatus.ACTIVE))).thenReturn(expired);
        expirationService.expireReservations();
        verify(r1, times(1)).setStatus(ReservationStatus.CANCELLED);
        verify(r2, times(1)).setStatus(ReservationStatus.CANCELLED);
        verify(reservationRepository, times(1)).saveAll(expired);
    }
}
