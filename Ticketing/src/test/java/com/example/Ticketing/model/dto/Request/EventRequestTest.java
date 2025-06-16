package com.example.Ticketing.model.dto.Request;

import com.example.Ticketing.Model.DTO.Request.EventRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EventRequestTest {
    @Test
    void testGetAndSetName() {
        EventRequest eventRequest = new EventRequest();
        String name = "Cinema Session";
        eventRequest.setName(name);
        assertEquals(name, eventRequest.getName());
    }

    @Test
    void testDefaultNameIsNull() {
        EventRequest eventRequest = new EventRequest();
        assertNull(eventRequest.getName());
    }
}
