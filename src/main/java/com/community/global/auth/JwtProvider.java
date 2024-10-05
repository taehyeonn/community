package com.community.global.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Component
public class JwtProvider {

    private final SecretKey secretKey;
    private final long maxAge;
    private final long refreshMaxAge;

    public JwtProvider(JwtProperties jwtProperties) {
        this.secretKey = generateSecretKey(jwtProperties.secret());
        this.maxAge = jwtProperties.maxAge();
        this.refreshMaxAge = jwtProperties.refreshMaxAge();
    }

    public String generateToken(String email, Map<String, Object> payload) {
        Claims claims = generateClaims(email, payload);
        return Jwts.builder()
                .signWith(secretKey)
                .claims(claims)
                .compact();
    }

    private SecretKey generateSecretKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims generateClaims(String email, Map<String, Object> payload) {
        Date now = new Date();
        Date expirationAt = new Date(now.getTime() + maxAge);
        return Jwts.claims()
                .subject(email)
                .issuedAt(now)
                .expiration(expirationAt)
                .add(payload)
                .build();
    }

    public String generateRefreshToken() {
        Claims claims = generateRefreshClaims();
        return Jwts.builder()
                .signWith(secretKey)
                .claims(claims)
                .compact();
    }

    private Claims generateRefreshClaims() {
        Date now = new Date();
        Date expirationAt = new Date(now.getTime() + refreshMaxAge);
        return Jwts.claims()
                .issuedAt(now)
                .expiration(expirationAt)
                .build();
    }

    public String getEmail(final String jwt) {
        Claims claims = getClaims(jwt);
        return Optional.ofNullable(claims.get("sub", String.class))
                .orElseThrow(() -> new RuntimeException("잘못된 토큰입니다."));
    }

    private Claims getClaims(final String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
