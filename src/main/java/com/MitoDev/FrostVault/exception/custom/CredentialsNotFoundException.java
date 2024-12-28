package com.MitoDev.FrostVault.exception.custom;


public class CredentialsNotFoundException extends CustomException{

    private static final int code = 403;

    public CredentialsNotFoundException() {
        super(code);
    }

    public CredentialsNotFoundException(String message) {
        super(message, code);
    }
}
