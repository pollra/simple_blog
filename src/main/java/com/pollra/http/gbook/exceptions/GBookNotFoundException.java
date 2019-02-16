package com.pollra.http.gbook.exceptions;

public class GBookNotFoundException extends GBookServiceException {
    public GBookNotFoundException(String message) {
        super(message);
    }

    public GBookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
