package com.example.Ticketing.Controller;

import com.example.Ticketing.Model.DTO.Request.PaymentRequest;
import com.example.Ticketing.Model.DTO.Response.PaymentResponse;
import com.example.Ticketing.Service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/payments")
@Tag(name = "Payment Processing", description = "Endpoint to handle payments with idempotency")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @Operation(summary = "Process a new payment", description = "Handles payment for a reservation using an idempotent request")
    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            @Valid @RequestBody PaymentRequest request,

            @Parameter(description = "Unique idempotency key to prevent duplicate payments", required = true, example = "f6b1c4e2-89d5-11ee-b9d1-0242ac120002")
            @RequestHeader("Idempotency-Key") String idempotencyKey) {

        PaymentResponse response = paymentService.processPayment(request, idempotencyKey);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "List payments by user")
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<PaymentResponse>> getPaymentsByUser(
            @Parameter(description = "ID of the user")
            @PathVariable Long userId,

            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Sort property", example = "createdAt")
            @RequestParam(defaultValue = "createdAt") String sort,

            @Parameter(description = "Sort direction", example = "desc")
            @RequestParam(defaultValue = "desc") String direction) {

        Sort.Direction sortDirection = "asc".equalsIgnoreCase(direction)
                ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortDirection, sort)
        );

        return ResponseEntity.ok(paymentService.getPaymentsByUser(userId, pageable));
    }
}
