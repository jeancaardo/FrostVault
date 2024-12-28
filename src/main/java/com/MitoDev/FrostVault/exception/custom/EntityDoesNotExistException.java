package com.MitoDev.FrostVault.exception.custom;

public class EntityDoesNotExistException extends CustomException {
    private static final int code = 404;
    private static final String msg = "%s with code %o does not exist.";
    public EntityDoesNotExistException(Class entityClass, Integer productId) {
        super(String.format(msg, entityClass.getName(), productId), code);
    }
}
