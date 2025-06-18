package com.example.Ticketing.Service;

import com.example.Ticketing.Exception.BusinessException;
import com.example.Ticketing.Exception.ResourceNotFoundException;
import com.example.Ticketing.Model.DTO.Request.EventRequest;
import com.example.Ticketing.Model.DTO.Response.EventResponse;
import com.example.Ticketing.Model.Entity.Event;
import com.example.Ticketing.Repository.EventRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

@Service
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);

    @Autowired
    EventRepository eventRepository;

    public EventResponse createEvent(EventRequest request) {
        log.info("Starting event creation process for: {}", request.getName());

        // Verificar se evento com mesmo nome já existe
        if (eventRepository.existsByName(request.getName())) {
            log.warn("Event creation failed: name already exists - {}", request.getName());
            throw new BusinessException("Event name already exists");
        }

        Event event = new Event();
        event.setName(request.getName());

        log.info("Saving new event to database: {}", request.getName());
        event = eventRepository.save(event);

        log.info("Event created successfully with ID: {}", event.getId());

        return convertToResponse(event);
    }

    public EventResponse getEventById(Long id) {
        log.info("Fetching event with ID: {}", id);

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Event not found with ID: {}", id);
                    return new ResourceNotFoundException("Event not found");
                });

        log.info("Event found: {}", event.getName());
        return convertToResponse(event);
    }

    public Page<EventResponse> listEvents(Pageable pageable) {
        log.info("Fetching paginated list of events. Page: {}, Size: {}", pageable.getPageNumber(), pageable.getPageSize());

        Page<EventResponse> responsePage = eventRepository.findAll(pageable)
                .map(this::convertToResponse);

        log.info("Number of events found: {}", responsePage.getTotalElements());
        return responsePage;
    }

    private EventResponse convertToResponse(Event event) {
        log.debug("Converting Event entity to EventResponse DTO. ID: {}", event.getId());

        return new EventResponse(
                event.getId(),
                event.getName(),
                event.getCreatedAt(),
                event.getUpdatedAt()
        );
    }
}
