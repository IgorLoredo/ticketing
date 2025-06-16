package com.example.Ticketing.controller;

import com.example.Ticketing.Controller.PaymentController;
import com.example.Ticketing.Model.DTO.Request.PaymentRequest;
import com.example.Ticketing.Model.DTO.Response.PaymentResponse;
import com.example.Ticketing.Service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PaymentControllerTest {
    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePayment() {
        PaymentRequest request = mock(PaymentRequest.class);
        PaymentResponse response = new PaymentResponse();
        String idempotencyKey = "test-key";

        when(paymentService.processPayment(any(PaymentRequest.class), eq(idempotencyKey))).thenReturn(response);

        ResponseEntity<PaymentResponse> result = paymentController.createPayment(request, idempotencyKey);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(paymentService, times(1)).processPayment(request, idempotencyKey);
    }

    @Test
    void testGetPaymentsByUser() {
        Long userId = 1L;
        int page = 0;
        int size = 10;
        String sort = "createdAt";
        String direction = "desc";
        Pageable pageable = PageRequest.of(page, size);
        PaymentResponse paymentResponse = new PaymentResponse();
        Page<PaymentResponse> pageResult = new PageImpl<>(Collections.singletonList(paymentResponse));

        when(paymentService.getPaymentsByUser(eq(userId), any(Pageable.class))).thenReturn(pageResult);

        ResponseEntity<Page<PaymentResponse>> result = paymentController.getPaymentsByUser(userId, page, size, sort, direction);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(pageResult, result.getBody());
        verify(paymentService, times(1)).getPaymentsByUser(eq(userId), any(Pageable.class));
    }
}
