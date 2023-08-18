package com.example.MyBookShopApp.security.service;

import com.example.MyBookShopApp.security.JWTBalacklistRepository;
import com.example.MyBookShopApp.security.JWTBlackList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTBlacklistService {

    private final JWTBalacklistRepository jwtBlacklistRepository;

    @Autowired
    public JWTBlacklistService(JWTBalacklistRepository jwtBlacklistRepository) {
        this.jwtBlacklistRepository = jwtBlacklistRepository;
    }

    /**
     * Saves the provided token to the JWTBlacklist repository if it is not null or empty.
     *
     * @param  token    the token to be saved
     */
    public void save(String token) {
        if(token != null && !token.isEmpty()) {
            JWTBlackList jwtBlackList = new JWTBlackList();
            jwtBlackList.setToken(token);
            jwtBlackList.setExpiredAt(new Date());
            jwtBlacklistRepository.save(jwtBlackList);
        }
    }

    /**
     * Determines if a token is old.
     *
     * @param  token  the token to be checked
     * @return        true if the token is old, false otherwise
     */
    public boolean isBlacklisted(String token) {
        JWTBlackList jwtBlackList = jwtBlacklistRepository.findByToken(token);
        return jwtBlackList != null && jwtBlackList.isExpiredAt().before(new Date());
    }
}
