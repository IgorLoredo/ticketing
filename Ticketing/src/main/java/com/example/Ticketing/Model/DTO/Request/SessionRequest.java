package com.example.Ticketing.Model.DTO.Request;

import com.example.Ticketing.Model.Enum.SeatType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class SessionRequest {
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

    @NotBlank(message = "Session name is required")
    @Size(min = 3, max = 100, message = "Session name must be 3-100 characters")
    private String name;

    @NotNull(message = "Start time is required")
    private String startTime;


    @Min(1)
    private int totalSeats;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal seatPrice;

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