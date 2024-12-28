package com.MitoDev.FrostVault.exception.custom;

public class CustomException extends RuntimeException{

    private final int code;
    public int getCode() {
        return code;
    }

    public CustomException(String message, int code) {
        super(message);
        this.code = code;
    }

    public CustomException(int code) {
        this.code = code;
    }
}
