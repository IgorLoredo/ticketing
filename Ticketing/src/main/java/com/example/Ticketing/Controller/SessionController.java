package com.example.Ticketing.Controller;

import com.example.Ticketing.Model.DTO.Request.SessionRequest;
import com.example.Ticketing.Model.DTO.Response.SessionResponse;
import com.example.Ticketing.Service.SessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/events/{eventId}/sessions")
@Tag(name = "Session Management", description = "Endpoints to manage sessions for a specific event")
@RequiredArgsConstructor
public class SessionController {

    @Autowired
    SessionService sessionService;

    @Operation(summary = "Create a new session for an event")
    @PostMapping
    public ResponseEntity<SessionResponse> createSession(
            @Parameter(description = "ID of the event", required = true, example = "101")
            @PathVariable Long eventId,

            @Valid @RequestBody SessionRequest request) {
        SessionResponse response = sessionService.createSession(eventId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get details of a specific session")
    @GetMapping("/{sessionId}")
    public ResponseEntity<SessionResponse> getSession(
            @Parameter(description = "ID of the event", required = true, example = "101")
            @PathVariable Long eventId,

            @Parameter(description = "ID of the session", required = true, example = "2001")
            @PathVariable Long sessionId) {
        return ResponseEntity.ok(sessionService.getSessionById(sessionId));
    }

    @Operation(summary = "List all sessions for an event with pagination and sorting")
    @GetMapping
    public ResponseEntity<Page<SessionResponse>> listSessions(
            @Parameter(description = "ID of the event", required = true, example = "101")
            @PathVariable Long eventId,

            @Parameter(description = "Page number (0-based index)", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Property to sort by", example = "startTime")
            @RequestParam(defaultValue = "startTime") String sort,

            @Parameter(description = "Sort direction (asc or desc)", example = "asc")
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction)
                ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortDirection, sort)
        );

        return ResponseEntity.ok(sessionService.getSessionsByEvent(eventId, pageable));
    }
}
