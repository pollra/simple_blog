package com.pollra.http.comment.exceptions.exception;

public class NotFoundException extends CommentServiceException {
    public NotFoundException(String message) {
        super(message);
    }
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
