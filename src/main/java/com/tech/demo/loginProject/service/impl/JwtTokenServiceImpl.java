package com.tech.demo.loginProject.service.impl;

import com.tech.demo.loginProject.model.User;
import com.tech.demo.loginProject.service.TokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenServiceImpl implements TokenService {
    @Override
    public String createToken(User user, Date currentDTTM) {
        String secretKey = "yourSecretKey";

        long expirationMillis = System.currentTimeMillis() + 3600000;

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());

        String token = Jwts.builder()
                .setSubject("exampleUser")
                .setIssuedAt(new Date())
                .setExpiration(new Date(expirationMillis))
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

       return token;
    }
}
