package com.bricks_ai_lms.bricks.ai.lms.services.auth.jwt;

import com.bricks_ai_lms.bricks.ai.lms.entities.UserEnt.RefreshToken;
import com.bricks_ai_lms.bricks.ai.lms.entities.UserEnt.User;
import com.bricks_ai_lms.bricks.ai.lms.repositories.Users.RefreshTokenRepository;
import com.bricks_ai_lms.bricks.ai.lms.repositories.Users.UserRepository;
import com.bricks_ai_lms.bricks.ai.lms.util.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${refreshToken.expiration}")
    private Long refreshTokenExpiration;

    @Value("${refreshToken.maxTokensPerUser:1}")
    private int maxTokensPerUser;

    @Transactional
    public RefreshToken createRefreshToken(UserDetails userDetails) {
        long activeTokenCount = refreshTokenRepository.countActiveTokensForUser(userDetails.getUsername());
        if (activeTokenCount >= maxTokensPerUser) {
            refreshTokenRepository.deactivateAllTokensForUser(userDetails.getUsername());
        }

        String tokenString = jwtUtil.generateRefreshToken(userDetails);

        LocalDateTime expiryDate = LocalDateTime.now()
                .plusSeconds(refreshTokenExpiration / 1000);

        RefreshToken refreshToken = new RefreshToken(tokenString, userDetails.getUsername(), expiryDate);
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByTokenAndIsActiveTrue(token);
    }

    public boolean validateRefreshToken(String token, UserDetails userDetails) {
        Optional<RefreshToken> refreshTokenOpt = findByToken(token);

        if (refreshTokenOpt.isEmpty()) {
            return false;
        }

        RefreshToken refreshToken = refreshTokenOpt.get();

        if (refreshToken.isExpired()) {
            deactivateToken(token);
            return false;
        }

        return jwtUtil.validateRefreshToken(token, userDetails);
    }

    @Transactional
    public void deactivateToken(String token) {
        refreshTokenRepository.deactivateToken(token);
    }

    @Transactional
    public void deactivateAllTokensForUser(String username) {
        refreshTokenRepository.deactivateAllTokensForUser(username);
    }

    @Transactional
    public void cleanupExpiredTokens() {
        refreshTokenRepository.deleteExpiredAndInactiveTokens(LocalDateTime.now());
    }

    public String refreshAccessToken(String refreshToken, UserDetails userDetails) {
        if (validateRefreshToken(refreshToken, userDetails)) {
            return jwtUtil.generateAccessToken(userDetails);
        }
        throw new RuntimeException("Invalid refresh token");
    }

    public Date getTokenExpiryDate(String token) {
        return jwtUtil.getExpirationDate(token);
    }
}