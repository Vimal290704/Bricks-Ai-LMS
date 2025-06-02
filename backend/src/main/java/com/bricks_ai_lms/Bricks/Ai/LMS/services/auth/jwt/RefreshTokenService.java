package com.bricks_ai_lms.bricks.ai.lms.services.auth.jwt;

import com.bricks_ai_lms.bricks.ai.lms.entities.UserEnt.RefreshToken;
import com.bricks_ai_lms.bricks.ai.lms.entities.UserEnt.User;
import com.bricks_ai_lms.bricks.ai.lms.repositories.Users.RefreshTokenRepository;
import com.bricks_ai_lms.bricks.ai.lms.repositories.Users.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenService {

    @Value("${refreshToken.expiration}")
    private Long refershTokenExpiration;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public RefreshToken createRefreshToken(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        refreshTokenRepository.findByUser(user).ifPresent(refreshTokenRepository::delete);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setExpiryDate(Instant.now().plusMillis(refershTokenExpiration));
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh Token was Expired! Please make a sign in request");
        }
        return token;
    }

    public void deleteByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        refreshTokenRepository.deleteByUser(user);
    }

    public RefreshToken rotateRefreshToken(RefreshToken oldToken) {
        refreshTokenRepository.delete(oldToken);
        RefreshToken newRefreshToken = new RefreshToken();
        newRefreshToken.setExpiryDate(Instant.now().plusMillis(refershTokenExpiration));
        newRefreshToken.setUser(oldToken.getUser());
        newRefreshToken.setToken(UUID.randomUUID().toString());
        return refreshTokenRepository.save(newRefreshToken);
    }
}
