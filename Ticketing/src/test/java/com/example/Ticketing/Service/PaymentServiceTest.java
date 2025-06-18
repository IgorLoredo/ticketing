package com.example.Ticketing.Service;

import com.example.Ticketing.Model.DTO.Request.PaymentRequest;
import com.example.Ticketing.Model.DTO.Response.PaymentResponse;
import com.example.Ticketing.Model.Entity.Payment;
import com.example.Ticketing.Model.Entity.Reservation;
import com.example.Ticketing.Model.Enum.PaymentMethod;
import com.example.Ticketing.Model.Enum.PaymentStatus;
import com.example.Ticketing.Repository.PaymentRepository;
import com.example.Ticketing.Repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private IdempotencyService idempotencyService;
    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPaymentsByUser() {
        Page<Payment> page = new PageImpl<>(Collections.emptyList());
        when(paymentRepository.findByUserId(eq(1L), any(Pageable.class))).thenReturn(page);
        Page<PaymentResponse> result = paymentService.getPaymentsByUser(1L, Pageable.unpaged());
        assertNotNull(result);
        verify(paymentRepository, times(1)).findByUserId(eq(1L), any(Pageable.class));
    }

    @Test
    void testProcessPayment_Success() {
        PaymentRequest request = mock(PaymentRequest.class);
        String idempotencyKey = "key-456";
        when(paymentRepository.existsByIdempotencyKey(idempotencyKey)).thenReturn(false);
        when(request.getReservationId()).thenReturn(1L);
        Reservation reservation = mock(Reservation.class);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservation.getStatus()).thenReturn(com.example.Ticketing.Model.Enum.ReservationStatus.ACTIVE);
        when(reservation.getExpirationTime()).thenReturn(java.time.LocalDateTime.now().plusMinutes(10));
        when(reservation.getSession()).thenReturn(mock(com.example.Ticketing.Model.Entity.Session.class));
        when(reservation.getSeatCount()).thenReturn(1);
        when(reservation.getProducts()).thenReturn(Collections.emptyList());
        when(request.getMethod()).thenReturn(PaymentMethod.PIX);
        when(request.getPaymentToken()).thenReturn("token");
        com.example.Ticketing.Model.Entity.Session session = mock(com.example.Ticketing.Model.Entity.Session.class);
        when(reservation.getSession()).thenReturn(session);
        when(session.getSeatPrice()).thenReturn(new BigDecimal("100.00"));
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setReservation(reservation);
        payment.setMethod(PaymentMethod.PIX);
        payment.setTransactionId("txid");
        payment.setAmount(new BigDecimal("100.00"));
        payment.setStatus(PaymentStatus.COMPLETED);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        PaymentResponse response = paymentService.processPayment(request, idempotencyKey);
        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }
}
