package com.example.MyBookShopApp.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTUtil {

    @Value("${auth.secret}")
    private String secretKey;

    public String createToken(Map<String, Object> claims, String username) {
        return Jwts
                .builder() // Создаем токен
                .setClaims(claims) // Устанавливаемся с переданными данными
                .setSubject(username)  //Имя пользователя
                .setIssuedAt(new Date(System.currentTimeMillis())) // Время создания
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Время истечения
                .signWith(SignatureAlgorithm.HS256, secretKey) // Ключ шифрования
                .compact(); // Готовый токен
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());//
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parser()  // Создаем парсер
                .setSigningKey(secretKey) // Наш ключ шифрования
                .parseClaimsJws(token) // Разбираем токен
                .getBody(); // Возвращаем что за блок
    }

    /*
     * this is to get the expiration date from the token
     * */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /*
     * this is to get the username from the token
     * */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); // возвращает строку с именем, записанным в токене.
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // возвращает true если истек, false если не истек
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token); //Extracting username from token
        return (username.equals(userDetails.getUsername()) &&!isTokenExpired(token));
    }

}
