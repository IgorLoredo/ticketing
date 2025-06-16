package com.example.Ticketing.Service;

import com.example.Ticketing.Model.DTO.Request.UserRequest;
import com.example.Ticketing.Model.DTO.Response.UserResponse;
import com.example.Ticketing.Model.Entity.User;
import com.example.Ticketing.Repository.UserRepository;
import com.example.Ticketing.Exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        UserRequest request = mock(UserRequest.class);
        when(request.getName()).thenReturn("John Doe");
        when(request.getEmail()).thenReturn("john@example.com");
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserResponse response = userService.createUser(request);
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("John Doe", response.getName());
        assertEquals("john@example.com", response.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setId(2L);
        user.setName("Jane Doe");
        user.setEmail("jane@example.com");
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        UserResponse response = userService.getUserById(2L);
        assertNotNull(response);
        assertEquals(2L, response.getId());
        assertEquals("Jane Doe", response.getName());
        assertEquals("jane@example.com", response.getEmail());
        verify(userRepository, times(1)).findById(2L);
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(99L));
    }

    @Test
    void testGetUserByEmail() {
        User user = new User();
        user.setId(3L);
        user.setName("Ana Teste");
        user.setEmail("ana@example.com");
        when(userRepository.findByEmail("ana@example.com")).thenReturn(Optional.of(user));
        UserResponse response = userService.getUserByEmail("ana@example.com");
        assertNotNull(response);
        assertEquals(3L, response.getId());
        assertEquals("Ana Teste", response.getName());
        assertEquals("ana@example.com", response.getEmail());
        verify(userRepository, times(1)).findByEmail("ana@example.com");
    }

    @Test
    void testGetUserByEmail_NotFound() {
        when(userRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserByEmail("notfound@example.com"));
    }
}
