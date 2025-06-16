package com.example.Ticketing.model.dto.Request;

import com.example.Ticketing.Model.DTO.Request.ReservationRequest;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ReservationRequestTest {
    @Test
    void testGetAndSetUserId() {
        ReservationRequest req = new ReservationRequest();
        req.setUserId(10L);
        assertEquals(10L, req.getUserId());
    }

    @Test
    void testGetAndSetSessionId() {
        ReservationRequest req = new ReservationRequest();
        req.setSessionId(20L);
        assertEquals(20L, req.getSessionId());
    }

    @Test
    void testGetAndSetSeatCount() {
        ReservationRequest req = new ReservationRequest();
        req.setSeatCount(3);
        assertEquals(3, req.getSeatCount());
    }

    @Test
    void testGetAndSetProductIds() {
        ReservationRequest req = new ReservationRequest();
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        req.setProductIds(ids);
        assertEquals(ids, req.getProductIds());
    }

    @Test
    void testDefaultValues() {
        ReservationRequest req = new ReservationRequest();
        assertNull(req.getUserId());
        assertNull(req.getSessionId());
        assertEquals(0, req.getSeatCount());
        assertNull(req.getProductIds());
    }
}
