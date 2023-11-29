package com.example.nbd.exceptions;

public class NoMatchingDeviceFoundException extends RuntimeException{
    public NoMatchingDeviceFoundException() {
        super("No matching device was found!");
    }
}
