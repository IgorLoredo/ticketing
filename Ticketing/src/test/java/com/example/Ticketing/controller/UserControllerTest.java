package com.example.Ticketing.controller;

import com.example.Ticketing.Model.DTO.Request.UserRequest;
import com.example.Ticketing.Model.DTO.Response.UserResponse;
import com.example.Ticketing.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private com.example.Ticketing.Controller.UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        UserRequest request = mock(UserRequest.class);
        UserResponse response = mock(UserResponse.class);
        when(userService.createUser(any(UserRequest.class))).thenReturn(response);

        ResponseEntity<UserResponse> result = userController.createUser(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(userService, times(1)).createUser(request);
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        UserResponse response = mock(UserResponse.class);
        when(userService.getUserById(userId)).thenReturn(response);

        ResponseEntity<UserResponse> result = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void testGetUserByEmail() {
        String email = "john@example.com";
        UserResponse response = mock(UserResponse.class);
        when(userService.getUserByEmail(email)).thenReturn(response);

        ResponseEntity<UserResponse> result = userController.getUserByEmail(email);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(userService, times(1)).getUserByEmail(email);
    }
}
