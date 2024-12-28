package com.MitoDev.FrostVault.exception.custom;

public class LoggedUserDoesNotExistException extends CustomException {

    private static final int code = 404;
    private static final String msg = "User with id %o does not exist.";

    public LoggedUserDoesNotExistException(Integer id) {
        super(String.format(msg, id), code);
    }
}
