package com.mateusz.zuk.currency.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception e) {
        ErrorResponse exception = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.internalServerError().body(exception);
    }
}