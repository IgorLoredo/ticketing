package com.example.Ticketing.model.dto.Response;

import com.example.Ticketing.Model.DTO.Response.SessionResponse;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class SessionResponseTest {
    @Test
    void testGetAndSetId() {
        SessionResponse response = new SessionResponse(10);
        response.setId(1L);
        assertEquals(1L, response.getId());
    }

    @Test
    void testGetAndSetName() {
        SessionResponse response = new SessionResponse(10);
        response.setName("Sessão 1");
        assertEquals("Sessão 1", response.getName());
    }

    @Test
    void testGetAndSetStartTime() {
        SessionResponse response = new SessionResponse(10);
        LocalDateTime now = LocalDateTime.now();
        response.setStartTime(now);
        assertEquals(now, response.getStartTime());
    }

    @Test
    void testGetAndSetTotalSeats() {
        SessionResponse response = new SessionResponse(10);
        response.setTotalSeats(100);
        assertEquals(100, response.getTotalSeats());
    }

    @Test
    void testGetAndSetAvailableSeats() {
        SessionResponse response = new SessionResponse(10);
        response.setAvailableSeats(50);
        assertEquals(50, response.getAvailableSeats());
    }

    @Test
    void testGetAndSetSeatPrice() {
        SessionResponse response = new SessionResponse(10);
        BigDecimal price = new BigDecimal("25.00");
        response.setSeatPrice(price);
        assertEquals(price, response.getSeatPrice());
    }

    @Test
    void testGetAndSetEventId() {
        SessionResponse response = new SessionResponse(10);
        response.setEventId(99L);
        assertEquals(99L, response.getEventId());
    }

    @Test
    void testGetAndSetEventName() {
        SessionResponse response = new SessionResponse(10);
        response.setEventName("Evento Teste");
        assertEquals("Evento Teste", response.getEventName());
    }

    @Test
    void testFullConstructor() {
        LocalDateTime now = LocalDateTime.now();
        SessionResponse response = new SessionResponse(1L, "Sessão", now, now, now, 100, 50, new BigDecimal("25.00"), 99L, "Evento Teste");
        assertEquals(1L, response.getId());
        assertEquals("Sessão", response.getName());
        assertEquals(now, response.getStartTime());
        assertEquals(now, response.getCreatedAt());
        assertEquals(now, response.getUpdatedAt());
        assertEquals(100, response.getTotalSeats());
        assertEquals(50, response.getAvailableSeats());
        assertEquals(new BigDecimal("25.00"), response.getSeatPrice());
        assertEquals(99L, response.getEventId());
        assertEquals("Evento Teste", response.getEventName());
    }
}
