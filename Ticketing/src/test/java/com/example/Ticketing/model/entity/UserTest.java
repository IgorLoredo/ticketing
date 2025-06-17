package com.example.Ticketing.model.entity;

import com.example.Ticketing.Model.Entity.User;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    void testGettersAndSetters() {
        User user = new User();
        Long id = 1L;
        String name = "Igor";
        String email = "igor@email.com";
        LocalDateTime createdAt = LocalDateTime.now();

        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setCreatedAt(createdAt);

        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(createdAt, user.getCreatedAt());
    }

    @Test
    void testDefaultValues() {
        User user = new User();
        assertNull(user.getId());
        assertNull(user.getName());
        assertNull(user.getEmail());
        assertNull(user.getCreatedAt());
    }
}
