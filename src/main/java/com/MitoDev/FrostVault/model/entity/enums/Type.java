package com.MitoDev.FrostVault.model.entity.enums;

import com.MitoDev.FrostVault.exception.custom.ProductTypeDoesNotExistException;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum Type {
    FRESH, FROZEN, COOLED, REGULAR;

    public static Type getTypeFromRawString(String s){
        return switch (s){
            case "FS" -> Type.FRESH;
            case "FF" -> Type.FROZEN;
            case "RF" -> Type.COOLED;
            default -> throw new ProductTypeDoesNotExistException(s);
        };
    }
}
