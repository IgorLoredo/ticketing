package com.example.Ticketing.Model.DTO.Response;

import com.example.Ticketing.Model.Enum.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReservationResponse {
    private Long id;
    private Long userId;
    private Long sessionId;
    private String sessionName;
    private int seatCount;
    private List<ProductResponse> products;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    private ReservationStatus status;

    public ReservationResponse(Long id, Long userId, Long sessionId, String sessionName, int seatCount, List<ProductResponse> products, BigDecimal totalPrice, LocalDateTime createdAt, ReservationStatus status) {
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