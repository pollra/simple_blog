package com.pollra.http.gbook.exceptions;

public class DataEntryException extends GBookServiceException {
    public DataEntryException(String message) {
        super(message);
    }

    public DataEntryException(String message, Throwable cause) {
        super(message, cause);
    }
}
