package com.pollra.http.gbook.exceptions;

public class UserNotFoundException extends GBookServiceException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
