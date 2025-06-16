package com.example.Ticketing.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IdempotencyServiceTest {
    private IdempotencyService service;

    @BeforeEach
    void setUp() {
        service = new IdempotencyService();
    }

    @Test
    void testKeyIsNotUsedInitially() {
        assertFalse(service.isKeyUsed("abc"));
    }

    @Test
    void testMarkKeyUsed() {
        String key = "key-123";
        service.markKeyUsed(key);
        assertTrue(service.isKeyUsed(key));
    }

    @Test
    void testCleanCache() {
        String key = "to-be-cleared";
        service.markKeyUsed(key);
        assertTrue(service.isKeyUsed(key));
        service.cleanCache();
        assertFalse(service.isKeyUsed(key));
    }
}
