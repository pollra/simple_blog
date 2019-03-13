package com.pollra.http.comment.exceptions.exception;

public class DataEntryException extends CommentServiceException {
    public DataEntryException(String message) {
        super(message);
    }

    public DataEntryException(String message, Throwable cause) {
        super(message, cause);
    }
}
