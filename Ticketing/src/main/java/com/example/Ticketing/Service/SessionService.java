package com.example.Ticketing.Service;

import com.example.Ticketing.Exception.BusinessException;
import com.example.Ticketing.Exception.ResourceNotFoundException;
import com.example.Ticketing.Model.DTO.Request.SessionRequest;
import com.example.Ticketing.Model.DTO.Response.SessionResponse;
import com.example.Ticketing.Model.Entity.Event;
import com.example.Ticketing.Model.Entity.Session;
import com.example.Ticketing.Repository.EventRepository;
import com.example.Ticketing.Repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Service
@Slf4j
public class SessionService {
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    EventRepository eventRepository;

    @Transactional
    public SessionResponse createSession(Long eventId, SessionRequest request) {

        // Buscar evento e validar existência
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));

        // Verificar se já existe sessão com mesmo nome no mesmo evento
        if (sessionRepository.existsByEventIdAndName(eventId, request.getName())) {
            throw new BusinessException("Session name already exists for this event");
        }

        // Definindo o formato esperado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Convertendo a string para LocalDateTime
        LocalDateTime data = LocalDateTime.parse(request.getStartTime(), formatter);

        // Criar nova sessão
        Session session = new Session();
        session.setName(request.getName());
        session.setStartTime(data);
        session.setEvent(event);
        session.setTotalSeats(request.getTotalSeats());
        session.setAvailableSeats(request.getTotalSeats()); // Todos os assentos disponíveis inicialmente
        session.setSeatPrice(request.getSeatPrice());

        session = sessionRepository.save(session);


        return convertToResponse(session);
    }

    public SessionResponse getSessionById(Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));

        return convertToResponse(session);
    }

    public Page<SessionResponse> getSessionsByEvent(Long eventId, Pageable pageable) {
        // Verificar se evento existe
        if (!eventRepository.existsById(eventId)) {
            throw new ResourceNotFoundException("Event not found with ID: " + eventId);
        }

        return sessionRepository.findByEventId(eventId, pageable)
                .map(this::convertToResponse);
    }

    private SessionResponse convertToResponse(Session session) {
        return new SessionResponse(
                session.getId(),
                session.getName(),
                session.getStartTime(),
                session.getCreatedAt(),
                session.getUpdatedAt(),
                session.getTotalSeats(),
                session.getAvailableSeats(),
                session.getSeatPrice(),
                session.getEvent().getId(),
                session.getEvent().getName()
                );
    }
}