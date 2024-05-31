package com.wallet.service.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.wallet.service.exception.NotEnoughMoneyException;

@ControllerAdvice
public class WalletNotFoundExceptionHandler {

    @ExceptionHandler(NotEnoughMoneyException.class)
    public ResponseEntity<String> WalletNotFoundException(NotEnoughMoneyException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
