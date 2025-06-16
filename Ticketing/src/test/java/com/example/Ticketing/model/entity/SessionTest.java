package com.example.Ticketing.model.entity;

import com.example.Ticketing.Model.Entity.Event;
import com.example.Ticketing.Model.Entity.Session;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class SessionTest {
    @Test
    void testGettersAndSetters() {
        Session session = new Session();
        Long id = 1L;
        String name = "Sessão Teste";
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        Event event = new Event();
        event.setId(2L);
        int totalSeats = 100;
        BigDecimal seatPrice = new BigDecimal("50.00");
        int availableSeats = 80;

        session.setId(id);
        session.setName(name);
        session.setStartTime(startTime);
        session.setCreatedAt(createdAt);
        session.setUpdatedAt(updatedAt);
        session.setEvent(event);
        session.setTotalSeats(totalSeats);
        session.setSeatPrice(seatPrice);
        session.setAvailableSeats(availableSeats);

        assertEquals(id, session.getId());
        assertEquals(name, session.getName());
        assertEquals(startTime, session.getStartTime());
        assertEquals(createdAt, session.getCreatedAt());
        assertEquals(updatedAt, session.getUpdatedAt());
        assertEquals(event, session.getEvent());
        assertEquals(totalSeats, session.getTotalSeats());
        assertEquals(seatPrice, session.getSeatPrice());
        assertEquals(availableSeats, session.getAvailableSeats());
    }

    @Test
    void testDefaultValues() {
        Session session = new Session();
        assertNull(session.getId());
        assertNull(session.getName());
        assertNull(session.getStartTime());
        assertNull(session.getCreatedAt());
        assertNull(session.getUpdatedAt());
        assertNull(session.getEvent());
        assertEquals(0, session.getTotalSeats());
        assertNull(session.getSeatPrice());
        assertEquals(0, session.getAvailableSeats());
    }
}
