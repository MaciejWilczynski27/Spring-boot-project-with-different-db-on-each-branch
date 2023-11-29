package com.example.nbd.exceptions;

public class DeviceAlreadyRentedException extends RuntimeException{
    public DeviceAlreadyRentedException() {
        super("Device is already rented");
    }
}
