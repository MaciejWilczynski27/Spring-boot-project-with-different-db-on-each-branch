package com.example.nbd.exceptions;

public class ClientIsNotActiveException extends RuntimeException {
    public ClientIsNotActiveException() {
        super("Client is not active");
    }
}
