package com.wallet.service.exception;

public class NotEnoughMoneyException extends RuntimeException{

    private String fieldName;
    private String fieldValue;

    public NotEnoughMoneyException(String fieldName, String fieldValue){
        super(String.format("Not enough money in the Wallet for %s: %s", fieldName, fieldValue));
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}

