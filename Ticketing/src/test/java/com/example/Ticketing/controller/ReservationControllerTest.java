package com.example.Ticketing.controller;

import com.example.Ticketing.Model.DTO.Request.ReservationRequest;
import com.example.Ticketing.Model.DTO.Response.ReservationResponse;
import com.example.Ticketing.Service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReservationControllerTest {
    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private com.example.Ticketing.Controller.ReservationController reservationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateReservation() {
        ReservationRequest request = mock(ReservationRequest.class);
        ReservationResponse response = mock(ReservationResponse.class);
        when(reservationService.createReservation(any(ReservationRequest.class))).thenReturn(response);

        ResponseEntity<ReservationResponse> result = reservationController.createReservation(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(reservationService, times(1)).createReservation(request);
    }

    @Test
    void testGetUserReservations() {
        Long userId = 1L;
        ReservationResponse reservationResponse = mock(ReservationResponse.class);
        List<ReservationResponse> responseList = Collections.singletonList(reservationResponse);
        when(reservationService.getUserReservations(userId)).thenReturn(responseList);

        ResponseEntity<List<ReservationResponse>> result = reservationController.getUserReservations(userId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody());
        verify(reservationService, times(1)).getUserReservations(userId);
    }

    @Test
    void testCancelReservation() {
        Long reservationId = 1L;
        doNothing().when(reservationService).cancelReservation(reservationId);

        ResponseEntity<Void> result = reservationController.cancelReservation(reservationId);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(reservationService, times(1)).cancelReservation(reservationId);
    }
}
