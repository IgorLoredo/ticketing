package com.example.Ticketing.Service;

import com.example.Ticketing.Exception.BusinessException;
import com.example.Ticketing.Exception.ResourceNotFoundException;
import com.example.Ticketing.Model.DTO.Request.EventRequest;
import com.example.Ticketing.Model.DTO.Response.EventResponse;
import com.example.Ticketing.Model.Entity.Event;
import com.example.Ticketing.Repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EventServiceTest {
    @Mock
    private EventRepository eventRepository;
    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEvent_Success() {
        EventRequest request = new EventRequest();
        request.setName("Evento Teste");
        Event event = new Event();
        event.setId(1L);
        event.setName("Evento Teste");
        event.setCreatedAt(LocalDateTime.now());
        event.setUpdatedAt(LocalDateTime.now());
        when(eventRepository.existsByName("Evento Teste")).thenReturn(false);
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        EventResponse response = eventService.createEvent(request);
        assertNotNull(response);
        assertEquals("Evento Teste", response.getName());
    }

    @Test
    void testCreateEvent_DuplicateName() {
        EventRequest request = new EventRequest();
        request.setName("Evento Existente");
        when(eventRepository.existsByName("Evento Existente")).thenReturn(true);
        assertThrows(BusinessException.class, () -> eventService.createEvent(request));
    }

    @Test
    void testGetEventById_Success() {
        Event event = new Event();
        event.setId(1L);
        event.setName("Evento");
        event.setCreatedAt(LocalDateTime.now());
        event.setUpdatedAt(LocalDateTime.now());
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        EventResponse response = eventService.getEventById(1L);
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Evento", response.getName());
    }

    @Test
    void testGetEventById_NotFound() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> eventService.getEventById(1L));
    }

    @Test
    void testListEvents() {
        Event event1 = new Event();
        event1.setId(1L);
        event1.setName("Evento 1");
        event1.setCreatedAt(LocalDateTime.now());
        event1.setUpdatedAt(LocalDateTime.now());
        Event event2 = new Event();
        event2.setId(2L);
        event2.setName("Evento 2");
        event2.setCreatedAt(LocalDateTime.now());
        event2.setUpdatedAt(LocalDateTime.now());
        Pageable pageable = PageRequest.of(0, 10);
        Page<Event> page = new PageImpl<>(Arrays.asList(event1, event2));
        when(eventRepository.findAll(pageable)).thenReturn(page);
        Page<EventResponse> responsePage = eventService.listEvents(pageable);
        assertEquals(2, responsePage.getTotalElements());
        assertEquals("Evento 1", responsePage.getContent().get(0).getName());
        assertEquals("Evento 2", responsePage.getContent().get(1).getName());
    }
}
