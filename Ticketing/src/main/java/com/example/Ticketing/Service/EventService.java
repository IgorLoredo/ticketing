package com.example.Ticketing.Service;

import com.example.Ticketing.Exception.BusinessException;
import com.example.Ticketing.Exception.ResourceNotFoundException;
import com.example.Ticketing.Model.DTO.Request.EventRequest;
import com.example.Ticketing.Model.DTO.Response.EventResponse;
import com.example.Ticketing.Model.Entity.Event;
import com.example.Ticketing.Repository.EventRepository;


import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;

@Service
@Log
public class EventService {
    @Autowired
     EventRepository eventRepository;


    public EventResponse createEvent(EventRequest request) {


        // Verificar se evento com mesmo nome já existe
        if (eventRepository.existsByName(request.getName())) {
            throw new BusinessException("Event name already exists");
        }

        Event event = new Event();
        event.setName(request.getName());
        event = eventRepository.save(event);


        return convertToResponse(event);
    }

    public EventResponse getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        return convertToResponse(event);
    }

    public Page<EventResponse> listEvents(Pageable pageable) {
        return eventRepository.findAll(pageable)
                .map(this::convertToResponse);
    }

    private EventResponse convertToResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getName(),
                event.getCreatedAt(),
                event.getUpdatedAt()
        );
    }
}