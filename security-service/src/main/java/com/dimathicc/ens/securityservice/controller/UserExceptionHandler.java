package com.dimathicc.ens.securityservice.controller;

import com.dimathicc.ens.securityservice.exception.UserCreateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@RestControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler({
            UserCreateException.class
    })
    public ResponseEntity<Exception> handleBadRequestException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
    }
}