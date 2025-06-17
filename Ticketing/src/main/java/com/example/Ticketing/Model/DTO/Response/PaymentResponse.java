package com.example.Ticketing.Model.DTO.Response;

import com.example.Ticketing.Model.Enum.PaymentMethod;
import com.example.Ticketing.Model.Enum.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PaymentResponse {

    @Schema(description = "Unique identifier of the payment", example = "1")
    private Long id;

    @Schema(description = "ID of the related reservation", example = "3")
    private Long reservationId;

    @Schema(description = "Payment method used", example = "CREDIT_CARD")
    private PaymentMethod method;

    @Schema(description = "Transaction ID from the payment gateway", example = "txn-abc-003")
    private String transactionId;

    @Schema(description = "Total payment amount", example = "85.00")
    private BigDecimal amount;

    @Schema(description = "Current payment status", example = "PAID")
    private PaymentStatus status;

    @Schema(description = "Date and time the payment was created", example = "2025-06-18T15:42:00")
    private LocalDateTime createdAt;

    @Schema(description = "List of products included in this payment")
    private List<ProductResponse> products;

    public PaymentResponse() {
    }

    public PaymentResponse(Long id, Long reservationId, PaymentMethod method, String transactionId, BigDecimal amount, PaymentStatus status) {
        this.id = id;
        this.reservationId = reservationId;
        this.method = method;
        this.transactionId = transactionId;
        this.amount = amount;
        this.status = status;
    }

    public PaymentResponse(Long id, Long reservationId, PaymentMethod method, String transactionId, BigDecimal amount, PaymentStatus status, LocalDateTime createdAt, List<ProductResponse> products) {
        this.id = id;
        this.reservationId = reservationId;
        this.method = method;
        this.transactionId = transactionId;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponse> products) {
        this.products = products;
    }
}
