package com.pollra.http.gbook.exceptions;

public abstract class GBookServiceException extends Exception{
    public GBookServiceException(String message) { super(message); }

    public GBookServiceException(String message, Throwable cause){ super(message, cause); }
}
