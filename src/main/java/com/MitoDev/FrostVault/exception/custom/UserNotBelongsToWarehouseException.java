package com.MitoDev.FrostVault.exception.custom;

public class UserNotBelongsToWarehouseException extends RuntimeException {

    private static final String msg = "User does not belong to warehouse with id %o";

    public UserNotBelongsToWarehouseException() {
    }

    public UserNotBelongsToWarehouseException(Integer id) {
        super(String.format(msg, id));
    }
}
