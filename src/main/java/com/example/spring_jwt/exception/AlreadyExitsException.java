package com.example.spring_jwt.exception;

public class AlreadyExitsException extends RuntimeException{
    public AlreadyExitsException(String message) {
        super(message);
    }
}
