package com.pollra.http.category.exception;

public class CategoryServerInternalException extends CategoryServiceException {

    public CategoryServerInternalException(String message) {
        super(message);
    }

    public CategoryServerInternalException(String message, Throwable cause) {
        super(message, cause);
    }
}
