package com.aspire.api.exception;

public class ManagerDoesNotExistException extends RuntimeException {
    public ManagerDoesNotExistException(String message) {
        super(message);
    }
}
