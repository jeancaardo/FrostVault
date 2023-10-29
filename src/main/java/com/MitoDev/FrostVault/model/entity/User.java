package com.MitoDev.FrostVault.model.entity;

import com.MitoDev.FrostVault.model.entity.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private Integer id;

    @Column(length = 25)
    private String username;

    private String password;

    @Column(length = 10)
    private Role role;
}
