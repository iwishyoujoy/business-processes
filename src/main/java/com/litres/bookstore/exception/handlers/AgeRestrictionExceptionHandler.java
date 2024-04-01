package com.litres.bookstore.exception.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.litres.bookstore.exception.AgeRestrictionException;

@ControllerAdvice
public class AgeRestrictionExceptionHandler {

    @ExceptionHandler(AgeRestrictionException.class)
    public ResponseEntity<String> handleAgeRestrictionException(AgeRestrictionException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }
}
