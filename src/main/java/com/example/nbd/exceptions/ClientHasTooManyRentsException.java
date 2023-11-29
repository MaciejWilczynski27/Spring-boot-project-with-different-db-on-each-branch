package com.example.nbd.exceptions;

public class ClientHasTooManyRentsException extends RuntimeException{
    public ClientHasTooManyRentsException() {
        super("Client has too many rents");
    }
}
