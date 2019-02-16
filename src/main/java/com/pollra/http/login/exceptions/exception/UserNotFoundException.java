package com.pollra.http.login.exceptions.exception;

public class UserNotFoundException extends UserServiceException{
    private static final long serialVersionUID = 1L;

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
