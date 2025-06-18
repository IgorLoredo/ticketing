package com.example.Ticketing.Exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BusinessExceptionTest {
    @Test
    void testMessage() {
        BusinessException ex = new BusinessException("erro de negócio");
        assertEquals("erro de negócio", ex.getMessage());
    }
}
