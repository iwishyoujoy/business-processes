package com.wallet.service.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.wallet.service.exception.WalletNotFoundException;

@ControllerAdvice
public class NotEnoughMoneyExceptionHandler {
    
    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<String> WalletNotFoundException(WalletNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
