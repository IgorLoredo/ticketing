package com.example.Ticketing.model.dto.Request;

import com.example.Ticketing.Model.DTO.Request.PaymentRequest;
import com.example.Ticketing.Model.Enum.PaymentMethod;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentRequestTest {
    @Test
    void testGetAndSetReservationId() {
        PaymentRequest paymentRequest = new PaymentRequest();
        Long reservationId = 123L;
        paymentRequest.setReservationId(reservationId);
        assertEquals(reservationId, paymentRequest.getReservationId());
    }

    @Test
    void testGetAndSetMethod() {
        PaymentRequest paymentRequest = new PaymentRequest();
        PaymentMethod method = PaymentMethod.CREDIT_CARD;
        paymentRequest.setMethod(method);
        assertEquals(method, paymentRequest.getMethod());
    }

    @Test
    void testGetAndSetPaymentToken() {
        PaymentRequest paymentRequest = new PaymentRequest();
        String token = "tok_abc123";
        paymentRequest.setPaymentToken(token);
        assertEquals(token, paymentRequest.getPaymentToken());
    }

    @Test
    void testDefaultValues() {
        PaymentRequest paymentRequest = new PaymentRequest();
        assertNull(paymentRequest.getReservationId());
        assertNull(paymentRequest.getMethod());
        assertNull(paymentRequest.getPaymentToken());
    }
}
