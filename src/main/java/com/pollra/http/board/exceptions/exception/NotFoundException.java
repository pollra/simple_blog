package com.pollra.http.board.exceptions.exception;

public class NotFoundException extends BoardServiceException{
    public NotFoundException(String message) {
        super(message);
    }
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
