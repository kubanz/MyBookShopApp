package com.example.MyBookShopApp.security;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JWTBalacklistRepository extends JpaRepository<JWTBlackList, Long> {
    JWTBlackList findByToken(String token);
}
