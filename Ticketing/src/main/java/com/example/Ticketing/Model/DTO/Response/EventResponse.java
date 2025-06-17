package com.example.Ticketing.Model.DTO.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventResponse {

    @Schema(description = "Unique identifier for the event", example = "1")
    private Long id;

    @Schema(description = "Name of the event", example = "Tech Conference 2025")
    private String name;

    @Schema(description = "Date and time when the event was created", example = "2025-06-01T14:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Date and time when the event was last updated", example = "2025-06-10T16:00:00")
    private LocalDateTime updatedAt;

    public EventResponse(Long id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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
}
