package com.example.Ticketing.model.dto.Request;

import com.example.Ticketing.Model.DTO.Request.SessionRequest;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class SessionRequestTest {
    @Test
    void testGetAndSetName() {
        SessionRequest req = new SessionRequest();
        req.setName("Spider-Man: No Way Home");
        assertEquals("Spider-Man: No Way Home", req.getName());
    }

    @Test
    void testGetAndSetStartTime() {
        SessionRequest req = new SessionRequest();
        req.setStartTime("2025-06-20T19:30:00");
        assertEquals("2025-06-20T19:30:00", req.getStartTime());
    }

    @Test
    void testGetAndSetTotalSeats() {
        SessionRequest req = new SessionRequest();
        req.setTotalSeats(120);
        assertEquals(120, req.getTotalSeats());
    }

    @Test
    void testGetAndSetSeatPrice() {
        SessionRequest req = new SessionRequest();
        BigDecimal price = new BigDecimal("25.00");
        req.setSeatPrice(price);
        assertEquals(price, req.getSeatPrice());
    }

    @Test
    void testDefaultValues() {
        SessionRequest req = new SessionRequest();
        assertNull(req.getName());
        assertNull(req.getStartTime());
        assertEquals(0, req.getTotalSeats());
        assertNull(req.getSeatPrice());
    }
}
