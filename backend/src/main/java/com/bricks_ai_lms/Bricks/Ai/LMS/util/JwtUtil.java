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
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationMs);
        Key key = getSignKey();

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key)
                .compact();
    }

    /**
     * Extract the subject (e.g. username/email) from the token. Returns null if parsing fails.
     */
    public String extractSubject(String token) {
        try {
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith((SecretKey) getSignKey())   // set the signing key for verification
                    .build()
                    .parseSignedClaims(token);
            return jws.getPayload().getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            // malformed, expired or unsupported JWT
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            // Parse (verifies signature and checks expiration)
            Jwts.parser().verifyWith((SecretKey) getSignKey()).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // invalid signature/claims, expired, or other parsing issue
            return false;
        }
    }

    public Key getSignKey() {
        byte[] keyBytes;
        try {
            // try Base64 decode (preferred if secret is stored encoded)
            keyBytes = Decoders.BASE64.decode(jwtSecret);
        } catch (Exception e) {
            // fallback to raw UTF-8 bytes (not recommended if key is short)
            keyBytes = jwtSecret.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        }
        try {
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (WeakKeyException we) {
            throw new IllegalArgumentException("JWT secret is too weak or short.", we);
        }
    }

    public String extractUsername(String jwt) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'extractUsername'");
    }
}
