package com.MitoDev.FrostVault.dataFactories;

import com.MitoDev.FrostVault.model.entity.User;
import com.MitoDev.FrostVault.model.entity.enums.Role;

public class UserFactory {

    public static User user1(){
        return User.builder()
                .id(1)
                .password("1234")
                .role(Role.WAREHOUSE_ADMIN)
                .username("pepito")
                .warehouse(WarehouseFactory.warehouse1())
                .build();
    }
}
