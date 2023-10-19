package com.example.nbd.exceptions;

public class DeviceAlreadyRentedException extends Exception{
    public DeviceAlreadyRentedException() {
        super("Device is already rented");
    }
}
