package com.MitoDev.FrostVault.exception.custom;

public class UserNotBelongsToWarehouseException extends RuntimeException {

    private static final String msg = "Warehouse with id %o does not exists";

    public UserNotBelongsToWarehouseException() {
    }

    public UserNotBelongsToWarehouseException(Integer id) {
        super(String.format(msg, id));
    }
}
