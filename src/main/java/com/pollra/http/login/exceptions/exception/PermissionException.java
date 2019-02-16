package com.pollra.http.login.exceptions.exception;

public class PermissionException extends UserServiceException{
    public PermissionException() {
    }

    public PermissionException(String message) {
        super(message);
    }
}
