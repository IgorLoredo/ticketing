package com.example.Ticketing.Exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResourceNotFoundExceptionTest {
    @Test
    void testMessage() {
        ResourceNotFoundException ex = new ResourceNotFoundException("não encontrado");
        assertEquals("não encontrado", ex.getMessage());
    }
}
