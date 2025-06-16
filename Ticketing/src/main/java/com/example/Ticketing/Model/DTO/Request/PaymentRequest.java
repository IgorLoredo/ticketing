package com.example.Ticketing.Model.DTO.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class PaymentRequest {
    @NotNull(message = "Reservation ID is required")
    @Positive(message = "Reservation ID must be positive")
    private Long reservationId;

    @NotBlank(message = "Payment token is required")
    private String paymentToken;

//    @Schema(description = "List of additional product IDs")
    private List<Long> productIds;

    // Not included in request body - set from header
    private String idempotencyKey;
}