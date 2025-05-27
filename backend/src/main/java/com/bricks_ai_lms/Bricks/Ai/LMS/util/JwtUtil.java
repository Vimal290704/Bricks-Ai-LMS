package com.bricks_ai_lms.bricks.ai.lms.util;

import java.security.Key;
import java.util.Date;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs:3600000}")
    private long jwtExpirationMs;

    public String generateToken(String subject) {
        Key key = getSignKey();
        return Jwts.builder()
                .signWith(key)
                .compact();
    }
    public String extractSubject(String token) {
        try {
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith((SecretKey) getSignKey())   // set the signing key for verification
                    .build()
                    .parseSignedClaims(token);
            return jws.getPayload().getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith((SecretKey) getSignKey()).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Key getSignKey() {
        byte[] keyBytes;
        try {
            keyBytes = Decoders.BASE64.decode(jwtSecret);
        } catch (Exception e) {
            keyBytes = jwtSecret.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        }
        try {
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (WeakKeyException we) {
            throw new IllegalArgumentException("JWT secret is too weak or short.", we);
        }
    }

    public String extractUsername(String jwt) {
        return extractSubject(jwt);
    }
}
