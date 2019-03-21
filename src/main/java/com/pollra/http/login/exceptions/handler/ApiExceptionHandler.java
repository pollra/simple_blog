package com.pollra.http.login.exceptions.handler;

import com.pollra.http.login.exceptions.domain.ApiErrorDetail;
import com.pollra.http.login.exceptions.exception.DataEntryException;
import com.pollra.http.login.exceptions.exception.PermissionException;
import com.pollra.http.login.exceptions.exception.UserNotFoundException;
import com.pollra.http.login.exceptions.exception.UserServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiErrorDetail> handleUserNotFoundException
            (UserNotFoundException unfe){
        ApiErrorDetail errorDetail = new ApiErrorDetail();
        errorDetail.setTimeStamp(new Date());
        errorDetail.setCode(1001);
        errorDetail.setMessage(unfe.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    // 서비스 자체 예외. 알수없음
    @ExceptionHandler(UserServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiErrorDetail> handleUserServiceException
            (UserServiceException use){
        ApiErrorDetail errorDetail = new ApiErrorDetail();
        errorDetail.setTimeStamp(new Date());
        errorDetail.setCode(9999);
        errorDetail.setMessage(use.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    // 데이터 처리 과정 예외
    @ExceptionHandler(DataEntryException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiErrorDetail> handleDataEntryException
            (DataEntryException dee){
        ApiErrorDetail errorDetail = new ApiErrorDetail();
        errorDetail.setTimeStamp(new Date());
        errorDetail.setCode(2001);
        errorDetail.setMessage(dee.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    // 권한 예외 처리.
    @ExceptionHandler(PermissionException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiErrorDetail> handlePermissionException
    (PermissionException dee){
        ApiErrorDetail errorDetail = new ApiErrorDetail();
        errorDetail.setTimeStamp(new Date());
        errorDetail.setCode(3001);
        errorDetail.setMessage(dee.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }
}
