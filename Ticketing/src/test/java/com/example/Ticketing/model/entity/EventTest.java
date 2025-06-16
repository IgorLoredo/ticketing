package com.example.Ticketing.model.entity;

import com.example.Ticketing.Model.Entity.Event;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    @Test
    void testGettersAndSetters() {
        Event event = new Event();
        Long id = 1L;
        String name = "Evento Teste";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        event.setId(id);
        event.setName(name);
        event.setCreatedAt(createdAt);
        event.setUpdatedAt(updatedAt);

        assertEquals(id, event.getId());
        assertEquals(name, event.getName());
        assertEquals(createdAt, event.getCreatedAt());
        assertEquals(updatedAt, event.getUpdatedAt());
    }

    @Test
    void testDefaultValues() {
        Event event = new Event();
        assertNull(event.getId());
        assertNull(event.getName());
        assertNull(event.getCreatedAt());
        assertNull(event.getUpdatedAt());
    }
}
