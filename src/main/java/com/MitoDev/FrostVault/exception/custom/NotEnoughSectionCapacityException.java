package com.MitoDev.FrostVault.exception.custom;


public class NotEnoughSectionCapacityException extends CustomException {
    private static final int code = 400;
    private static final String msg = "Section with code %o has not enough capacity for all batches";
    public NotEnoughSectionCapacityException(Integer sectionCode) {
        super(String.format(msg, sectionCode), code);
    }
}
