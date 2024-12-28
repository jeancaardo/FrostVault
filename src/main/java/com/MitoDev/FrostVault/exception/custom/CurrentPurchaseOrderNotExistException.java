package com.MitoDev.FrostVault.exception.custom;

public class CurrentPurchaseOrderNotExistException extends CustomException {

    private static final int code = 404;
    private static final String msg = "No current purchase order with status cart for user with id %o";

    public CurrentPurchaseOrderNotExistException(Integer id) {
        super(String.format(msg, id), code);
    }
}
