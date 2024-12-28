package com.MitoDev.FrostVault.exception.custom;

public class ProductNotPresentInCartException extends CustomException {

    private static final int code = 404;
    private static final String msg = "Product with id %o does not exist in the current cart.";
    public ProductNotPresentInCartException(Integer sectionCode) {
        super(String.format(msg, sectionCode), code);
    }
}
