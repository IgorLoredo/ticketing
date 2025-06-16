package com.example.Ticketing.Model.DTO.Response;

import com.example.Ticketing.Model.Enum.SeatType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class SessionResponse {
    private Long id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime createdAt;
    private int totalSeats;
    private int availableSeats;


    private BigDecimal seatPrice;
    private LocalDateTime updatedAt;
    private Long eventId;
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
    public SessionResponse(int availableSeats) {
        this.availableSeats = availableSeats;
    }

}