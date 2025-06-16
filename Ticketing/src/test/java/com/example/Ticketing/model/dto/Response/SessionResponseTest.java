package com.example.Ticketing.model.dto.Response;

import com.example.Ticketing.Model.DTO.Response.SessionResponse;
import com.example.Ticketing.Model.DTO.Response.ProductResponse;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SessionResponseTest {
    @Test
    void testGetAndSetId() {
        SessionResponse response = new SessionResponse();
        response.setId(1L);
        assertEquals(1L, response.getId());
    }

    @Test
    void testGetAndSetName() {
        SessionResponse response = new SessionResponse();
        response.setName("Sessão 1");
        assertEquals("Sessão 1", response.getName());
    }

    @Test
    void testGetAndSetStartTime() {
        SessionResponse response = new SessionResponse();
        LocalDateTime now = LocalDateTime.now();
        response.setStartTime(now);
        assertEquals(now, response.getStartTime());
    }

    @Test
    void testGetAndSetTotalSeats() {
        SessionResponse response = new SessionResponse();
        response.setTotalSeats(100);
        assertEquals(100, response.getTotalSeats());
    }

    @Test
    void testGetAndSetAvailableSeats() {
        SessionResponse response = new SessionResponse();
        response.setAvailableSeats(50);
        assertEquals(50, response.getAvailableSeats());
    }

    @Test
    void testGetAndSetSeatPrice() {
        SessionResponse response = new SessionResponse();
        BigDecimal price = new BigDecimal("25.00");
        response.setSeatPrice(price);
        assertEquals(price, response.getSeatPrice());
    }

    @Test
    void testGetAndSetProducts() {
        SessionResponse response = new SessionResponse();
        List<ProductResponse> products = List.of(new ProductResponse(1L, "Soda", new BigDecimal("5.50")));
        response.setProducts(products);
        assertEquals(products, response.getProducts());
    }

    @Test
    void testGetAndSetEventId() {
        SessionResponse response = new SessionResponse();
        response.setEventId(99L);
        assertEquals(99L, response.getEventId());
    }
}
