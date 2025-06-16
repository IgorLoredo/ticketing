package com.example.Ticketing.controller;

import com.example.Ticketing.Model.DTO.Request.SessionRequest;
import com.example.Ticketing.Model.DTO.Response.SessionResponse;
import com.example.Ticketing.Service.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class SessionControllerTest {
    @Mock
    private SessionService sessionService;

    @InjectMocks
    private com.example.Ticketing.Controller.SessionController sessionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSession() {
        Long eventId = 1L;
        SessionRequest request = mock(SessionRequest.class);
        SessionResponse response = mock(SessionResponse.class);
        when(sessionService.createSession(eq(eventId), any(SessionRequest.class))).thenReturn(response);

        ResponseEntity<SessionResponse> result = sessionController.createSession(eventId, request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(sessionService, times(1)).createSession(eq(eventId), eq(request));
    }

    @Test
    void testGetSession() {
        Long eventId = 1L;
        Long sessionId = 2L;
        SessionResponse response = mock(SessionResponse.class);
        when(sessionService.getSessionById(sessionId)).thenReturn(response);

        ResponseEntity<SessionResponse> result = sessionController.getSession(eventId, sessionId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(sessionService, times(1)).getSessionById(sessionId);
    }

    @Test
    void testListSessions() {
        Long eventId = 1L;
        int page = 0;
        int size = 10;
        String sort = "startTime";
        String direction = "asc";
        Pageable pageable = PageRequest.of(page, size);
        SessionResponse sessionResponse = mock(SessionResponse.class);
        Page<SessionResponse> pageResult = new PageImpl<>(Collections.singletonList(sessionResponse));

        when(sessionService.getSessionsByEvent(eq(eventId), any(Pageable.class))).thenReturn(pageResult);

        ResponseEntity<Page<SessionResponse>> result = sessionController.listSessions(eventId, page, size, sort, direction);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(pageResult, result.getBody());
        verify(sessionService, times(1)).getSessionsByEvent(eq(eventId), any(Pageable.class));
    }
}
