package com.MitoDev.FrostVault.dataFactories;

import com.MitoDev.FrostVault.model.entity.Warehouse;

public class WarehouseFactory {

    public static Warehouse warehouse1(){
        return Warehouse.builder()
                .id(1)
                .address("Sanma 1234")
                .build();
    }

    public static Warehouse warehouse2(){
        return Warehouse.builder()
                .id(2)
                .address("Belgrano 123")
                .build();
    }
}
