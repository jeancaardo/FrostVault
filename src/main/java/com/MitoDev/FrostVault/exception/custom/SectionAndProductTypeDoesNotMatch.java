package com.MitoDev.FrostVault.exception.custom;

public class SectionAndProductTypeDoesNotMatch extends CustomException {
    private static final int code = 400;
    private static final String msg = "Section type with code %o does not match with product type with id %o";
    public SectionAndProductTypeDoesNotMatch(Integer sectionCode, Integer productId) {
        super(String.format(msg, sectionCode, productId), code);
    }
}
