package com.rest.api.store.exception;

public class StoreInvalidCredentialsException extends RuntimeException {
    public StoreInvalidCredentialsException(String message) {
        super(message);
    }
}
