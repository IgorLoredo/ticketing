package com.example.Ticketing.model.dto.Response;

import com.example.Ticketing.Model.DTO.Response.EventResponse;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class EventResponseTest {
    @Test
    void testGetAndSetId() {
        EventResponse response = new EventResponse(null, null, null, null);
        response.setId(10L);
        assertEquals(10L, response.getId());
    }

    @Test
    void testGetAndSetName() {
        EventResponse response = new EventResponse(null, null, null, null);
        response.setName("Evento Teste");
        assertEquals("Evento Teste", response.getName());
    }

    @Test
    void testGetAndSetCreatedAt() {
        EventResponse response = new EventResponse(null, null, null, null);
        LocalDateTime now = LocalDateTime.now();
        response.setCreatedAt(now);
        assertEquals(now, response.getCreatedAt());
    }

    @Test
    void testGetAndSetUpdatedAt() {
        EventResponse response = new EventResponse(null, null, null, null);
        LocalDateTime now = LocalDateTime.now();
        response.setUpdatedAt(now);
        assertEquals(now, response.getUpdatedAt());
    }

    @Test
    void testConstructor() {
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime updated = LocalDateTime.now();
        EventResponse response = new EventResponse(1L, "Cinema", created, updated);
        assertEquals(1L, response.getId());
        assertEquals("Cinema", response.getName());
        assertEquals(created, response.getCreatedAt());
        assertEquals(updated, response.getUpdatedAt());
    }
}
