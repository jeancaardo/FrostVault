package com.MitoDev.FrostVault.exception.custom;


public class CredentialsNotFoundException extends RuntimeException{

    public CredentialsNotFoundException() {
    }

    public CredentialsNotFoundException(String message) {
        super(message);
    }
}
