package com.example.Ticketing.Model.DTO.Request;

import com.example.Ticketing.Model.Enum.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class PaymentRequest {


    @NotNull
    private Long reservationId;

    @NotNull
    private PaymentMethod method;

    @NotBlank
    private String paymentToken; // Token do gateway de pagamento

    // IdempotencyKey vem do header, não do body
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

    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
    }
}
