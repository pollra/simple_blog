package com.pollra.http.comment.exceptions.exception;

public abstract class CommentServiceException extends RuntimeException{
    public CommentServiceException(String message) {super(message);}
    public CommentServiceException(String message, Throwable cause) {super(message, cause);}
}
