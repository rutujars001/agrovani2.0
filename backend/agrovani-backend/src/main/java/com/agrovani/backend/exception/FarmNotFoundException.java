package com.agrovani.backend.exception;

public class FarmNotFoundException extends ResourceNotFoundException {

    public FarmNotFoundException(String message) {
        super(message);
    }
}