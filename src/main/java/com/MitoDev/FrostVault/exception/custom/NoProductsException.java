package com.MitoDev.FrostVault.exception.custom;

public class NoProductsException extends CustomException {
    private static final int code = 404;
    private static final String msg = "There are no products in database";

    private static final String msgCategory = "There are no products with category %s";
    public NoProductsException(String cat) {
        super(String.format(msgCategory, cat), code);
    }

    public NoProductsException() {
        super(String.format(msg), code);
    }

}
