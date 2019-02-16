package com.pollra.http.category.exception;

public class DataEntryException extends CategoryServiceException {
    public DataEntryException(String message) {
        super(message);
    }

    public DataEntryException(String message, Throwable cause) {
        super(message, cause);
    }
}
