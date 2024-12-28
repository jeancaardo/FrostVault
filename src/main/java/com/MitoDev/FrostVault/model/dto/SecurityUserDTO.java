package com.MitoDev.FrostVault.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityUserDTO {
    private String username;

    private String token;
}
