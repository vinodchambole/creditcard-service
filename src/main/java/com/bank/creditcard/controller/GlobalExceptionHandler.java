package com.bank.creditcard.controller;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.annotation.Priority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
@Priority(-1)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException ex) {
        String errorMessage = "JWT token has expired";
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(Exception ex) {
        log.error("Error: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("internal server error: " + ex.getLocalizedMessage());
    }
}
