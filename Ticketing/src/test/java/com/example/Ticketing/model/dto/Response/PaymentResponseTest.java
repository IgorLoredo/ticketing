package com.example.Ticketing.model.dto.Response;

import com.example.Ticketing.Model.DTO.Response.PaymentResponse;
import com.example.Ticketing.Model.Enum.PaymentMethod;
import com.example.Ticketing.Model.Enum.PaymentStatus;
import com.example.Ticketing.Model.DTO.Response.ProductResponse;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentResponseTest {
    @Test
    void testGetAndSetId() {
        PaymentResponse response = new PaymentResponse(1L, 2L, null, "tx", new BigDecimal("10.00"), null);
        response.setId(10L);
        assertEquals(10L, response.getId());
    }

    @Test
    void testGetAndSetReservationId() {
        PaymentResponse response = new PaymentResponse(1L, 2L, null, "tx", new BigDecimal("10.00"), null);
        response.setReservationId(20L);
        assertEquals(20L, response.getReservationId());
    }

    @Test
    void testGetAndSetMethod() {
        PaymentResponse response = new PaymentResponse(1L, 2L, null, "tx", new BigDecimal("10.00"), null);
        response.setMethod(PaymentMethod.PIX);
        assertEquals(PaymentMethod.PIX, response.getMethod());
    }

    @Test
    void testGetAndSetTransactionId() {
        PaymentResponse response = new PaymentResponse(1L, 2L, null, "tx", new BigDecimal("10.00"), null);
        response.setTransactionId("tx123");
        assertEquals("tx123", response.getTransactionId());
    }

    @Test
    void testGetAndSetAmount() {
        PaymentResponse response = new PaymentResponse(1L, 2L, null, "tx", new BigDecimal("10.00"), null);
        response.setAmount(new BigDecimal("99.99"));
        assertEquals(new BigDecimal("99.99"), response.getAmount());
    }

    @Test
    void testGetAndSetStatus() {
        PaymentResponse response = new PaymentResponse(1L, 2L, null, "tx", new BigDecimal("10.00"), null);
        response.setStatus(PaymentStatus.COMPLETED);
        assertEquals(PaymentStatus.COMPLETED, response.getStatus());
    }

    @Test
    void testGetAndSetCreatedAt() {
        PaymentResponse response = new PaymentResponse(1L, 2L, null, "tx", new BigDecimal("10.00"), null);
        LocalDateTime now = LocalDateTime.now();
        response.setCreatedAt(now);
        assertEquals(now, response.getCreatedAt());
    }

    @Test
    void testGetAndSetProducts() {
        PaymentResponse response = new PaymentResponse(1L, 2L, null, "tx", new BigDecimal("10.00"), null);
        List<ProductResponse> products = List.of();
        response.setProducts(products);
        assertNotNull(response.getProducts());
    }

    @Test
    void testFullConstructor() {
        LocalDateTime created = LocalDateTime.now();
        List<ProductResponse> products = List.of();
        PaymentResponse response = new PaymentResponse(1L, 2L, PaymentMethod.PIX, "txid", new BigDecimal("50.00"), PaymentStatus.COMPLETED, created, products);
        assertEquals(1L, response.getId());
        assertEquals(2L, response.getReservationId());
        assertEquals(PaymentMethod.PIX, response.getMethod());
        assertEquals("txid", response.getTransactionId());
        assertEquals(new BigDecimal("50.00"), response.getAmount());
        assertEquals(PaymentStatus.COMPLETED, response.getStatus());
        assertEquals(created, response.getCreatedAt());
        assertEquals(products, response.getProducts());
    }
}
