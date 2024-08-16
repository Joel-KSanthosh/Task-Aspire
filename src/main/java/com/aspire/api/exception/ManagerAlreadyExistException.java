package com.aspire.api.exception;

public class ManagerAlreadyExistException extends RuntimeException {
    public ManagerAlreadyExistException(String message) {
        super(message);
    }
}
