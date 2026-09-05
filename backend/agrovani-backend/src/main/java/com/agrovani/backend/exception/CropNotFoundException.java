package com.agrovani.backend.exception;

public class CropNotFoundException extends ResourceNotFoundException {

    public CropNotFoundException(String message) {
        super(message);
    }
}