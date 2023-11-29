package com.example.nbd.exceptions;

public class InvalidDatesException extends RuntimeException {
    public InvalidDatesException() {
        super("The dates are invalid");
    }
}
