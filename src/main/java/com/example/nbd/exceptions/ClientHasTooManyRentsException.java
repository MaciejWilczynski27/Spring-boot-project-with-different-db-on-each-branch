package com.example.nbd.exceptions;

public class ClientHasTooManyRentsException extends Exception{
    public ClientHasTooManyRentsException() {
        super("Client has too many rents");
    }
}
