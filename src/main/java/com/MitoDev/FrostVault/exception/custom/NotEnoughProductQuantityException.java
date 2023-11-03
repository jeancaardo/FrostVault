package com.MitoDev.FrostVault.exception.custom;

public class NotEnoughProductQuantityException extends CustomException {

    private static final int code = 400;
    private static final String msg = "There are not enough products with id %o to fulfill order";

    public NotEnoughProductQuantityException(Integer id) {
        super(String.format(msg, id), code);
    }
}
