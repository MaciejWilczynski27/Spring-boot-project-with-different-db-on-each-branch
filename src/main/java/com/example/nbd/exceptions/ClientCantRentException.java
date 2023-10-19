package com.example.nbd.exceptions;

public class ClientCantRentException extends Exception{
    public ClientCantRentException() {
        super("Client is inactive or has too many rents");
    }
}
