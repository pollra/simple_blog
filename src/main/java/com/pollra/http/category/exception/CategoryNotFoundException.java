package com.pollra.http.category.exception;

public class CategoryNotFoundException extends CategoryServiceException {
    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
