package com.example.Ticketing.model.dto.Response;

import com.example.Ticketing.Model.DTO.Response.ReservationResponse;
import com.example.Ticketing.Model.DTO.Response.ProductResponse;
import com.example.Ticketing.Model.Enum.ReservationStatus;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ReservationResponseTest {
    @Test
    void testConstructorAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        List<ProductResponse> products = List.of(new ProductResponse(1L, "Pipoca", new BigDecimal("10.00")));
        ReservationResponse response = new ReservationResponse(1L, 2L, 3L, "Sessão Teste", 4, products, new BigDecimal("99.99"), now, ReservationStatus.CANCELLED);
        assertEquals(1L, response.getId());
        assertEquals(2L, response.getUserId());
        assertEquals(3L, response.getSessionId());
        assertEquals("Sessão Teste", response.getSessionName());
        assertEquals(4, response.getSeatCount());
        assertEquals(products, response.getProducts());
        assertEquals(new BigDecimal("99.99"), response.getTotalPrice());
        assertEquals(now, response.getCreatedAt());
        assertEquals(ReservationStatus.CANCELLED, response.getStatus());
    }
}
