package com.agrovani.backend.exception;

public class FarmerNotFoundException extends ResourceNotFoundException {

    public FarmerNotFoundException(String message) {
        super(message);
    }
}