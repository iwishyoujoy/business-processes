package com.wallet.service.exception;

public class WalletNotFoundException extends RuntimeException{

    private String fieldName;
    private String fieldValue;

    public WalletNotFoundException(String fieldName, String fieldValue){
        super(String.format("Wallet with %s: '%s' not found!", fieldName, fieldValue));
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
