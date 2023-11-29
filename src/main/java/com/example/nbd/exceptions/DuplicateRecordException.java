package com.example.nbd.exceptions;

public class DuplicateRecordException extends RuntimeException{
    public DuplicateRecordException() {
        super("Record already exists");
    }
}
