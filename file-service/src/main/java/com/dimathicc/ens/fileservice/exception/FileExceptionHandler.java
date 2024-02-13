package com.dimathicc.ens.fileservice.exception;

import com.dimathicc.ens.fileservice.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class FileExceptionHandler {
    @ExceptionHandler({
            FileDownloadException.class,
            ReadingUserFromXlsxException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception e) {
        return defaultErrorResponse(e, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> defaultErrorResponse(Exception e, HttpStatus httpStatus) {
        ErrorResponse response = new ErrorResponse(httpStatus.value(), e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, httpStatus);
    }
}
