package com.common_service.configuration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.amazon.exception.CustomException;

@RestControllerAdvice
public class XGlobalExceptionHandler {

    @ExceptionHandler(XCustomException.class)
    public ResponseEntity<XCustomException> handleCustomException(XCustomException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(ex);
    }
}