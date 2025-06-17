package com.example.Ticketing.Controller;

import com.example.Ticketing.Model.DTO.Request.ReservationRequest;
import com.example.Ticketing.Model.DTO.Response.ReservationResponse;
import com.example.Ticketing.Service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@Tag(name = "Reservation Management", description = "Endpoints to manage reservations")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @Operation(summary = "Create a new reservation")
    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @Parameter(description = "Reservation request body", required = true)
            @Valid @RequestBody ReservationRequest request) {
        ReservationResponse response = reservationService.createReservation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get all reservations for a specific user")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationResponse>> getUserReservations(
            @Parameter(description = "ID of the user", required = true)
            @PathVariable Long userId) {
        return ResponseEntity.ok(reservationService.getUserReservations(userId));
    }

    @Operation(summary = "Cancel a reservation by ID")
    @PostMapping("/{reservationId}/cancel")
    public ResponseEntity<Void> cancelReservation(
            @Parameter(description = "ID of the reservation to cancel", required = true)
            @PathVariable Long reservationId) {
        reservationService.cancelReservation(reservationId);
        return ResponseEntity.noContent().build();
    }
}
