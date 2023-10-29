package com.MitoDev.FrostVault.repository;

import com.MitoDev.FrostVault.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsernameEqualsAndPassword(String username, String password);
}
