package com.pollra.http.category.exception;

public abstract class CategoryServiceException extends Exception {
    public CategoryServiceException(String message) {
        super(message);
    }
    public CategoryServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
