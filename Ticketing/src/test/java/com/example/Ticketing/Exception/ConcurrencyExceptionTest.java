package com.example.Ticketing.Exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConcurrencyExceptionTest {
    @Test
    void testMessage() {
        ConcurrencyException ex = new ConcurrencyException("concorrência");
        assertEquals("concorrência", ex.getMessage());
    }
}
