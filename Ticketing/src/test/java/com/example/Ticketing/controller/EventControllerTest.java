package com.example.Ticketing.controller;

import com.example.Ticketing.Model.DTO.Request.EventRequest;
import com.example.Ticketing.Model.DTO.Response.EventResponse;
import com.example.Ticketing.Service.EventService;
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

class EventControllerTest {
    @Mock
    private EventService eventService;

    @InjectMocks
    private com.example.Ticketing.Controller.EventController eventController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEvent() {
        EventRequest request = mock(EventRequest.class);
        EventResponse response = mock(EventResponse.class);
        when(eventService.createEvent(any(EventRequest.class))).thenReturn(response);

        ResponseEntity<EventResponse> result = eventController.createEvent(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(eventService, times(1)).createEvent(request);
    }

    @Test
    void testGetEventById() {
        Long eventId = 1L;
        EventResponse response = mock(EventResponse.class);
        when(eventService.getEventById(eventId)).thenReturn(response);

        ResponseEntity<EventResponse> result = eventController.getEventById(eventId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(eventService, times(1)).getEventById(eventId);
    }

    @Test
    void testListEvents() {
        int page = 0;
        int size = 10;
        String sort = "name";
        String direction = "asc";
        Pageable pageable = PageRequest.of(page, size);
        EventResponse eventResponse = mock(EventResponse.class);
        Page<EventResponse> pageResult = new PageImpl<>(Collections.singletonList(eventResponse));

        when(eventService.listEvents(any(Pageable.class))).thenReturn(pageResult);

        ResponseEntity<Page<EventResponse>> result = eventController.listEvents(page, size, sort, direction);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(pageResult, result.getBody());
        verify(eventService, times(1)).listEvents(any(Pageable.class));
    }
}
