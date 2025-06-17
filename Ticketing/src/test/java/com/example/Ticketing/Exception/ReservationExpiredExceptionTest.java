package com.example.Ticketing.Exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReservationExpiredExceptionTest {
    @Test
    void testMessage() {
        ReservationExpiredException ex = new ReservationExpiredException("expirada");
        assertEquals("expirada", ex.getMessage());
    }
}
