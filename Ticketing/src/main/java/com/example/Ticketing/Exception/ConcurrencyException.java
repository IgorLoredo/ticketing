package com.example.Ticketing.Exception;


public class ConcurrencyException extends RuntimeException {
    public ConcurrencyException(String message) { super(message); }
}