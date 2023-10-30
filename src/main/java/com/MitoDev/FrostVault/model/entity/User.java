package com.MitoDev.FrostVault.model.entity;

import com.MitoDev.FrostVault.model.entity.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    @ManyToOne
    private Warehouse warehouse;
}
