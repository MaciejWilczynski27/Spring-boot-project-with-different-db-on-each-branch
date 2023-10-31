package com.example.nbd.exceptions;

public class InvalidDatesException extends Exception {
    public InvalidDatesException() {
        super("The dates are invalid");
    }
}
