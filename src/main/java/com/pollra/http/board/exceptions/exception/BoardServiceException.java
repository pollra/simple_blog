package com.pollra.http.board.exceptions.exception;

public abstract class BoardServiceException extends RuntimeException{
    public BoardServiceException(String message) {super(message);}
    public BoardServiceException(String message, Throwable cause) {super(message, cause);}
}
