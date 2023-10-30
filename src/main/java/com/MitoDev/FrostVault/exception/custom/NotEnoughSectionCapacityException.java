package com.MitoDev.FrostVault.exception.custom;


public class NotEnoughSectionCapacityException extends RuntimeException {

    private static final String msg = "Section with code %o has not enough capacity for all batches";
    public NotEnoughSectionCapacityException(Integer sectionCode) {
        super(String.format(msg, sectionCode));
    }
}
