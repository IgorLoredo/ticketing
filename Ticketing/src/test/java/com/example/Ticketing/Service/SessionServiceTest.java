package com.example.Ticketing.Service;

import com.example.Ticketing.Exception.BusinessException;
import com.example.Ticketing.Exception.ResourceNotFoundException;
import com.example.Ticketing.Model.DTO.Request.SessionRequest;
import com.example.Ticketing.Model.DTO.Response.SessionResponse;
import com.example.Ticketing.Model.Entity.Event;
import com.example.Ticketing.Model.Entity.Session;
import com.example.Ticketing.Repository.EventRepository;
import com.example.Ticketing.Repository.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SessionServiceTest {
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private EventRepository eventRepository;
    @InjectMocks
    private SessionService sessionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSession_Success() {
        Long eventId = 1L;
        SessionRequest request = new SessionRequest();
        request.setName("Session 1");
        request.setStartTime("2025-06-18 10:00:00");
        request.setTotalSeats(100);
        request.setSeatPrice(BigDecimal.TEN);

        Event event = new Event();
        event.setId(eventId);
        event.setName("Event 1");

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(sessionRepository.existsByEventIdAndName(eventId, "Session 1")).thenReturn(false);
        when(sessionRepository.save(any(Session.class))).thenAnswer(invocation -> {
            Session s = invocation.getArgument(0);
            s.setId(1L);
            s.setCreatedAt(LocalDateTime.now());
            s.setUpdatedAt(LocalDateTime.now());
            return s;
        });

        SessionResponse response = sessionService.createSession(eventId, request);
        assertNotNull(response);
        assertEquals("Session 1", response.getName());
        assertEquals(100, response.getTotalSeats());
        assertEquals(BigDecimal.TEN, response.getSeatPrice());
        assertEquals(eventId, response.getEventId());
    }

    @Test
    void testCreateSession_EventNotFound() {
        Long eventId = 1L;
        SessionRequest request = new SessionRequest();
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> sessionService.createSession(eventId, request));
    }

    @Test
    void testCreateSession_DuplicateSessionName() {
        Long eventId = 1L;
        SessionRequest request = new SessionRequest();
        request.setName("Session 1");
        Event event = new Event();
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(sessionRepository.existsByEventIdAndName(eventId, "Session 1")).thenReturn(true);
        assertThrows(BusinessException.class, () -> sessionService.createSession(eventId, request));
    }

    @Test
    void testGetSessionById_Success() {
        Long sessionId = 1L;
        Session session = new Session();
        session.setId(sessionId);
        session.setName("Session 1");
        session.setStartTime(LocalDateTime.now());
        session.setCreatedAt(LocalDateTime.now());
        session.setUpdatedAt(LocalDateTime.now());
        session.setTotalSeats(100);
        session.setAvailableSeats(100);
        session.setSeatPrice(BigDecimal.TEN);
        Event event = new Event();
        event.setId(2L);
        event.setName("Event 2");
        session.setEvent(event);
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        SessionResponse response = sessionService.getSessionById(sessionId);
        assertNotNull(response);
        assertEquals(sessionId, response.getId());
        assertEquals("Session 1", response.getName());
        assertEquals(2L, response.getEventId());
    }

    @Test
    void testGetSessionById_NotFound() {
        Long sessionId = 1L;
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> sessionService.getSessionById(sessionId));
    }

    @Test
    void testGetSessionsByEvent_Success() {
        Long eventId = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        Event event = new Event();
        event.setId(eventId);
        when(eventRepository.existsById(eventId)).thenReturn(true);
        Session session = new Session();
        session.setId(1L);
        session.setName("Session 1");
        session.setStartTime(LocalDateTime.now());
        session.setCreatedAt(LocalDateTime.now());
        session.setUpdatedAt(LocalDateTime.now());
        session.setTotalSeats(100);
        session.setAvailableSeats(100);
        session.setSeatPrice(BigDecimal.TEN);
        session.setEvent(event);
        Page<Session> page = new PageImpl<>(Arrays.asList(session));
        when(sessionRepository.findByEventId(eventId, pageable)).thenReturn(page);
        Page<SessionResponse> responsePage = sessionService.getSessionsByEvent(eventId, pageable);
        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals("Session 1", responsePage.getContent().get(0).getName());
    }

    @Test
    void testGetSessionsByEvent_EventNotFound() {
        Long eventId = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        when(eventRepository.existsById(eventId)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> sessionService.getSessionsByEvent(eventId, pageable));
    }
}
