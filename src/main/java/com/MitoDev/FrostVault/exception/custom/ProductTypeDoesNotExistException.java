package com.MitoDev.FrostVault.exception.custom;

public class ProductTypeDoesNotExistException extends CustomException{
    private static final int code = 400;
    private static final String msg = "Product type %s does not exist. Use \"FF\", \"FS\" or \"RF\" instead.";

    public ProductTypeDoesNotExistException(String s) {
        super(String.format(msg, s), code);
    }
}
