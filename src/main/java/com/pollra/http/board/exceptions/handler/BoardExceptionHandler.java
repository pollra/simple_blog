package com.pollra.http.board.exceptions.handler;

import com.pollra.http.board.exceptions.exception.PermissionException;
import com.pollra.http.board.exceptions.exception.BoardServiceException;
import com.pollra.http.board.exceptions.exception.DataEntryException;
import com.pollra.http.board.exceptions.exception.NotFoundException;
import com.pollra.http.login.exceptions.domain.ApiErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@ControllerAdvice
public class BoardExceptionHandler {
    @ExceptionHandler(BoardServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiErrorDetail> handleUserNotFoundException
            (BoardServiceException bse){
        ApiErrorDetail errorDetail = new ApiErrorDetail();
        errorDetail.setTimeStamp(new Date());
        errorDetail.setCode(0001);
        errorDetail.setMessage(bse.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(PermissionException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiErrorDetail> handleUserNotFoundException
            (PermissionException pe){
        ApiErrorDetail errorDetail = new ApiErrorDetail();
        errorDetail.setTimeStamp(new Date());
        errorDetail.setCode(1001);
        errorDetail.setMessage(pe.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(DataEntryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorDetail> handleUserNotFoundException
            (DataEntryException dee){
        ApiErrorDetail errorDetail = new ApiErrorDetail();
        errorDetail.setTimeStamp(new Date());
        errorDetail.setCode(2001);
        errorDetail.setMessage(dee.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiErrorDetail> handleUserNotFoundException
            (NotFoundException nfe){
        ApiErrorDetail errorDetail = new ApiErrorDetail();
        errorDetail.setTimeStamp(new Date());
        errorDetail.setCode(3001);
        errorDetail.setMessage(nfe.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }
}
