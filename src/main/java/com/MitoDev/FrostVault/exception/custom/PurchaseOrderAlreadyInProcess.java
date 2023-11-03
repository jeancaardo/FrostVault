package com.MitoDev.FrostVault.exception.custom;

public class PurchaseOrderAlreadyInProcess extends CustomException {

    private static final int code = 400;
    private static final String msg = "There is already a purchase order in progress for user %o. Confirm it or delete products inside it.";

    public PurchaseOrderAlreadyInProcess(Integer id) {
        super(String.format(msg, id), code);
    }
}
