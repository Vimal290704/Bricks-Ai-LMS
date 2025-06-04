package com.bricks_ai_lms.bricks.ai.lms.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${accessToken.expiration}")
    private Long accessTokenExpiration;

    @Value(("${refreshToken.expiration}"))
    private Long refreshTokenExpiration;

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "access");
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .and()
                .signWith(getKey())
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh");
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .and()
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().
                verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    public boolean validateAccessToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        final String tokenType = extractTokenType(token);
        return (username.equals(userDetails.getUsername())
                && TokenNotExpired(token)
                && "access".equals(tokenType));
    }

    public boolean validateRefreshToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        final String tokenType = extractTokenType(token);
        return (username.equals(userDetails.getUsername())
                && TokenNotExpired(token)
                && "refresh".equals(tokenType));
    }

    public boolean TokenNotExpired(String token) {
        return !extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Date getExpirationDate(String token) {
        return extractExpiration(token);
    }

    public String extractTokenType(String token) {
        return extractClaim(token, claims -> claims.get("type", String.class));
    }
}

