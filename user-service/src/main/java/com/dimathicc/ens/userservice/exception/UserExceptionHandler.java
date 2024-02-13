package com.dimathicc.ens.userservice.exception;

import com.dimathicc.ens.userservice.dto.ErrorResponse;
import com.dimathicc.ens.userservice.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler({
            UserRegistrationException.class,
            UserUpdateException.class,
            UserValidationException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception e) {
        return defaultErrorResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception e) {
        return defaultErrorResponse(e, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponse> defaultErrorResponse(Exception e, HttpStatus httpStatus) {
        ErrorResponse response = new ErrorResponse(httpStatus.value(), e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, httpStatus);
    }
}
