package com.example.nbd.exceptions;

public class ClientIsNotActiveException extends Exception {
    public ClientIsNotActiveException() {
        super("Client is not active");
    }
}
