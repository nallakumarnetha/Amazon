package com.order_service.configuration;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomException> handleCustomException(CustomException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(ex);
    }
}