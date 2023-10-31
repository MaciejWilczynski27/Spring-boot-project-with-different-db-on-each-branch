package com.example.nbd.exceptions;

public class NoMatchingDeviceFoundException extends Exception{
    public NoMatchingDeviceFoundException() {
        super("No matching device was found!");
    }
}
