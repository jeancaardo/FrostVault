package com.MitoDev.FrostVault.model.entity;

import com.MitoDev.FrostVault.model.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
