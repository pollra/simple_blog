package com.pollra.http.login.exceptions.exception;

public class UserServiceException extends RuntimeException {
    public UserServiceException() {
    }

    public UserServiceException(String message) {
        super(message);
    }
}
