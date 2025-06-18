package com.example.Ticketing.Exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleResourceNotFound() {
        ResponseEntity<String> response = handler.handleResourceNotFound(new ResourceNotFoundException("not found"));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("not found", response.getBody());
    }

    @Test
    void testHandleBusinessException() {
        ResponseEntity<String> response = handler.handleBusinessException(new BusinessException("business error"));
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("business error", response.getBody());
    }

    @Test
    void testHandleConcurrencyException() {
        ResponseEntity<String> response = handler.handleConcurrencyException(new ConcurrencyException("concorrência"));
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("concorrência", response.getBody());
        assertTrue(response.getHeaders().containsKey("Retry-After"));
    }

    @Test
    void testHandleReservationExpired() {
        ResponseEntity<String> response = handler.handleReservationExpired(new ReservationExpiredException("expirada"));
        assertEquals(HttpStatus.GONE, response.getStatusCode());
        assertEquals("expirada", response.getBody());
    }

    // O teste de validação de argumentos não é trivial sem um mock de MethodArgumentNotValidException
}
