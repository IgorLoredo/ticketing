package com.example.Ticketing.model.dto.Response;

import com.example.Ticketing.Model.DTO.Response.UserResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserResponseTest {
    @Test
    void testGetAndSetId() {
        UserResponse response = new UserResponse(1L, "John Doe", "john@example.com");
        response.setId(2L);
        assertEquals(2L, response.getId());
    }

    @Test
    void testGetAndSetName() {
        UserResponse response = new UserResponse(1L, "John Doe", "john@example.com");
        response.setName("Jane Doe");
        assertEquals("Jane Doe", response.getName());
    }

    @Test
    void testGetAndSetEmail() {
        UserResponse response = new UserResponse(1L, "John Doe", "john@example.com");
        response.setEmail("jane@example.com");
        assertEquals("jane@example.com", response.getEmail());
    }

    @Test
    void testConstructor() {
        UserResponse response = new UserResponse(1L, "John Doe", "john@example.com");
        assertEquals(1L, response.getId());
        assertEquals("John Doe", response.getName());
        assertEquals("john@example.com", response.getEmail());
    }
}
