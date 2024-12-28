package com.MitoDev.FrostVault.controller;

import com.MitoDev.FrostVault.model.dto.SecurityUserDTO;
import com.MitoDev.FrostVault.exception.custom.CredentialsNotFoundException;
import com.MitoDev.FrostVault.model.entity.User;
import com.MitoDev.FrostVault.repository.IUserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private ObjectMapper objectMapper = new ObjectMapper();

    private IUserRepository userRepository;

    public UserController(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("user")
    public SecurityUserDTO login(@RequestParam("user") String username, @RequestParam("password") String pwd) throws JsonProcessingException {

        User user = userRepository.findByUsernameEqualsAndPassword(username, pwd)
                .orElseThrow(() -> {throw new CredentialsNotFoundException("Wrong credentials for user '"+username+"'. Try again. ");});

        String token = getJWTToken(user);
        SecurityUserDTO userDto = new SecurityUserDTO();
        userDto.setUsername(username);
        userDto.setToken(token);
        return userDto;

    }

    @GetMapping("hola")
    public String hola(){
        return "hola";
    }

    private String getJWTToken(User user) throws JsonProcessingException {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_"+user.getRole().toString());

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(objectMapper.writeValueAsString(user))
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}
