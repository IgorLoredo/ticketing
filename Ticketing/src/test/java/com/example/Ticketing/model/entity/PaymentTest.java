package com.example.Ticketing.model.entity;

import com.example.Ticketing.Model.Entity.Payment;
import com.example.Ticketing.Model.Entity.Reservation;
import com.example.Ticketing.Model.Enum.PaymentMethod;
import com.example.Ticketing.Model.Enum.PaymentStatus;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentTest {
    @Test
    void testGettersAndSetters() {
        Payment payment = new Payment();
        Long id = 1L;
        Reservation reservation = mock(Reservation.class);
        PaymentMethod method = PaymentMethod.PIX;
        String transactionId = "txid";
        BigDecimal amount = new BigDecimal("100.00");
        PaymentStatus status = PaymentStatus.COMPLETED;
        LocalDateTime createdAt = LocalDateTime.now();
        String idempotencyKey = "key-123";

        payment.setId(id);
        payment.setReservation(reservation);
        payment.setMethod(method);
        payment.setTransactionId(transactionId);
        payment.setAmount(amount);
        payment.setStatus(status);
        payment.setCreatedAt(createdAt);
        payment.setIdempotencyKey(idempotencyKey);

        assertEquals(id, payment.getId());
        assertEquals(reservation, payment.getReservation());
        assertEquals(method, payment.getMethod());
        assertEquals(transactionId, payment.getTransactionId());
        assertEquals(amount, payment.getAmount());
        assertEquals(status, payment.getStatus());
        assertEquals(createdAt, payment.getCreatedAt());
        assertEquals(idempotencyKey, payment.getIdempotencyKey());
    }

    @Test
    void testDefaultValues() {
        Payment payment = new Payment();
        assertNull(payment.getId());
        assertNull(payment.getReservation());
        assertNull(payment.getMethod());
        assertNull(payment.getTransactionId());
        assertNull(payment.getAmount());
        assertEquals(PaymentStatus.PENDING, payment.getStatus());
        assertNull(payment.getCreatedAt());
        assertNull(payment.getIdempotencyKey());
    }
}
