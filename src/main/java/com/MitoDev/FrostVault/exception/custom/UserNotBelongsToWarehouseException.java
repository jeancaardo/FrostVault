package com.MitoDev.FrostVault.exception.custom;

public class UserNotBelongsToWarehouseException extends CustomException {
    private static final int code = 400;
    private static final String msg = "User does not belong to warehouse with id %o";

    public UserNotBelongsToWarehouseException() {
        super(code);
    }

    public UserNotBelongsToWarehouseException(Integer id) {
        super(String.format(msg, id), code);
    }
}
