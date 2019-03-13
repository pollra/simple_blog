package com.pollra.http.comment.exceptions.exception;

public class PermissionException extends CommentServiceException {
    public PermissionException(String message) {
        super(message);
    }

    public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}
