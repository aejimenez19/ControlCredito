package com.aejimenez19.auth_service.configuration;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    private final String jwtSecret = "Q5d8JzYvB+MH4ZKQ9Nd4w5HzvnU5oQfRrZ3wU6Qka7YJz+Pu3EDvXL8YX+7UoRDLHeKl0/UsXqgCB3fB4M5Lxw==";
    private final long jwtExpirationMs = 3600000;

    public String generateToken(String username, UUID personaId) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", personaId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
