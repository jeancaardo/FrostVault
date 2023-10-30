package com.MitoDev.FrostVault.exception.custom;

public class SectionAndProductTypeDoesNotMatch extends RuntimeException {

    private static final String msg = "Section type with code %o does not match with product type with id %o";
    public SectionAndProductTypeDoesNotMatch(Integer sectionCode, Integer productId) {
        super(String.format(msg, sectionCode, productId));
    }
}
