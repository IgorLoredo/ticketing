package com.example.Ticketing.Model.DTO.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SessionResponse {

    @Schema(description = "Unique identifier of the session", example = "1")
    private Long id;

    @Schema(description = "Name of the session", example = "Avengers Screening")
    private String name;

    @Schema(description = "Date and time the session starts", example = "2025-06-20T19:00:00")
    private LocalDateTime startTime;

    @Schema(description = "Timestamp of session creation", example = "2025-06-10T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Total number of seats for the session", example = "100")
    private int totalSeats;

    @Schema(description = "Number of seats still available", example = "85")
    private int availableSeats;

    @Schema(description = "Price of a single seat", example = "25.00")
    private BigDecimal seatPrice;

    @Schema(description = "Timestamp of last update", example = "2025-06-12T14:45:00")
    private LocalDateTime updatedAt;

    @Schema(description = "ID of the event associated with this session", example = "1")
    private Long eventId;

    @Schema(description = "Name of the event associated with this session", example = "Cinema Night")
    private String eventName;

    public SessionResponse(Long id, String name, LocalDateTime startTime, LocalDateTime createdAt, LocalDateTime updatedAt, int totalSeats, int availableSeats, BigDecimal seatPrice, Long eventId, String eventName) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.createdAt = createdAt;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.seatPrice = seatPrice;
        this.updatedAt = updatedAt;
        this.eventId = eventId;
        this.eventName = eventName;
    }

    public SessionResponse(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public BigDecimal getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(BigDecimal seatPrice) {
        this.seatPrice = seatPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
