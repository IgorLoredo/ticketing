package com.example.Ticketing.Model.DTO.Request;

import jakarta.validation.constraints.*;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Data
@Schema(description = "Request object to create a new reservation")
public class ReservationRequest {



    @NotNull(message = "User ID is required")
    @Schema(description = "ID of the user making the reservation", example = "1")
    private Long userId;

    @NotNull(message = "Session ID is required")
    @Schema(description = "ID of the session being reserved", example = "10")
    private Long sessionId;

    @Min(value = 1, message = "At least one seat is required")
    @Schema(description = "Number of seats to reserve", example = "2", minimum = "1")
    private int seatCount;

    @Schema(description = "List of optional product IDs added to the reservation", example = "[100, 101]")
    private List<Long> productIds;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}
