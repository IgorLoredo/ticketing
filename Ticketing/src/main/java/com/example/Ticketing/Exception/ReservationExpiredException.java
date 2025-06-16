package com.example.Ticketing.Exception;

public class ReservationExpiredException extends RuntimeException {
    public ReservationExpiredException(String message) { super(message); }
}