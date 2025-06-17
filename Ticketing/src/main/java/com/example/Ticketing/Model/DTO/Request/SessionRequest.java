package com.example.Ticketing.Model.DTO.Request;

import jakarta.validation.constraints.*;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Data
@Schema(description = "Request object for creating a new session")
public class SessionRequest {

    @NotBlank(message = "Session name is required")
    @Size(min = 3, max = 100, message = "Session name must be 3-100 characters")
    @Schema(description = "Name of the session", example = "Spider-Man: No Way Home")
    private String name;

    @NotNull(message = "Start time is required")
    @Schema(description = "Session start time in ISO 8601 format", example = "2025-06-20T19:30:00")
    private String startTime;

    @Min(value = 1, message = "Total seats must be at least 1")
    @Schema(description = "Total number of seats available for the session", example = "120", minimum = "1")
    private int totalSeats;

    @NotNull(message = "Seat price is required")
    @DecimalMin(value = "0.01", message = "Seat price must be at least 0.01")
    @Schema(description = "Price per seat", example = "25.00", minimum = "0.01")
    private BigDecimal seatPrice;

    // Getters and Setters (manually defined)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public BigDecimal getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(BigDecimal seatPrice) {
        this.seatPrice = seatPrice;
    }
}
