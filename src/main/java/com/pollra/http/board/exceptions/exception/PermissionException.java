package com.pollra.http.board.exceptions.exception;

public class PermissionException extends BoardServiceException{
    public PermissionException(String message) {
        super(message);
    }

    public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}
