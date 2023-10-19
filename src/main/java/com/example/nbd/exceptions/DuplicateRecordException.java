package com.example.nbd.exceptions;

public class DuplicateRecordException extends Exception{
    public DuplicateRecordException() {
        super("Record already exists");
    }
}
