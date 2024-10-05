package com.community.global.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
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

    public String generateToken(Long memberId) {
        Claims claims = generateClaims(memberId);
        return Jwts.builder()
                .signWith(secretKey)
                .claims(claims)
                .compact();
    }

    private SecretKey generateSecretKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims generateClaims(Long memberId) {
        Date now = new Date();
        Date expirationAt = new Date(now.getTime() + maxAge);
        return Jwts.claims()
                .subject(String.valueOf(memberId))
                .issuedAt(now)
                .expiration(expirationAt)
                .build();
    }

    public ResponseCookie generateRefreshToken(Long memberId) {
        Claims claims = generateRefreshClaims(memberId);
        String refreshToken = Jwts.builder()
                .signWith(secretKey)
                .claims(claims)
                .compact();

        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict")
                .maxAge(new Date().getTime() + refreshMaxAge)
                .path("/")
                .build();
    }

    private Claims generateRefreshClaims(Long memberId) {
        Date now = new Date();
        Date expirationAt = new Date(now.getTime() + refreshMaxAge);
        return Jwts.claims()
                .subject(String.valueOf(memberId))
                .issuedAt(now)
                .expiration(expirationAt)
                .build();
    }

    public String getMemberId(final String token) {
        Claims claims = getClaims(token);
        return Optional.ofNullable(claims.get("sub", String.class))
                .orElseThrow(() -> new RuntimeException("잘못된 토큰입니다."));
    }

    private Claims getClaims(final String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(refreshToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
