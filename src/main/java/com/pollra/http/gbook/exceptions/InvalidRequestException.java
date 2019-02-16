package com.pollra.http.gbook.exceptions;

public class InvalidRequestException extends GBookServiceException {
    public InvalidRequestException(String message) {
        super(message);
    }

    public InvalidRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
