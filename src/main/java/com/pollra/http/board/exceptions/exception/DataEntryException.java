package com.pollra.http.board.exceptions.exception;

public class DataEntryException extends BoardServiceException {
    public DataEntryException(String message) {
        super(message);
    }

    public DataEntryException(String message, Throwable cause) {
        super(message, cause);
    }
}
