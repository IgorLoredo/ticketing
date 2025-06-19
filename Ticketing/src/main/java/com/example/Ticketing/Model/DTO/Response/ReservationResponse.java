package com.example.Ticketing.Model.DTO.Response;

import com.example.Ticketing.Model.Enum.ReservationStatus;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "Response object representing a reservation")
public class ReservationResponse {

    @Schema(description = "Reservation ID", example = "5001")
    private Long id;

    @Schema(description = "User ID who made the reservation", example = "1")
    private Long userId;

    @Schema(description = "Session ID related to the reservation", example = "100")
    private Long sessionId;

    @Schema(description = "Name of the session", example = "Avengers: Endgame - 7:00 PM")
    private String sessionName;

    @Schema(description = "Number of seats reserved", example = "3")
    private int seatCount;

    @Schema(description = "List of optional products associated with the reservation")
    private List<ProductResponse> products;

    @Schema(description = "Total price of the reservation", example = "45.00")
    private BigDecimal totalPrice;

    @Schema(description = "Date and time when the reservation was created", example = "2025-06-18T16:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Status of the reservation", example = "CONFIRMED")
    private ReservationStatus status;

    // Constructor
    public ReservationResponse(Long id, Long userId, Long sessionId, String sessionName, int seatCount,
                               List<ProductResponse> products, BigDecimal totalPrice,
                               LocalDateTime createdAt, ReservationStatus status) {
        this.id = id;
        this.userId = userId;
        this.sessionId = sessionId;
        this.sessionName = sessionName;
        this.seatCount = seatCount;
        this.products = products;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.status = status;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponse> products) {
        this.products = products;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

}
