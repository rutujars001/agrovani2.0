package com.agrovani.backend.exception;

public class DuplicatePhoneNumberException extends DuplicateResourceException {

    public DuplicatePhoneNumberException(String message) {
        super(message);
    }
}