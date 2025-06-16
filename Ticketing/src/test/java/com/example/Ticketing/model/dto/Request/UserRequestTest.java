package com.example.Ticketing.model.dto.Request;

import com.example.Ticketing.Model.DTO.Request.UserRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserRequestTest {
    @Test
    void testGetAndSetName() {
        UserRequest req = new UserRequest();
        req.setName("John Doe");
        assertEquals("John Doe", req.getName());
    }

    @Test
    void testGetAndSetEmail() {
        UserRequest req = new UserRequest();
        req.setEmail("john@example.com");
        assertEquals("john@example.com", req.getEmail());
    }

    @Test
    void testDefaultValues() {
        UserRequest req = new UserRequest();
        assertNull(req.getName());
        assertNull(req.getEmail());
    }
}
