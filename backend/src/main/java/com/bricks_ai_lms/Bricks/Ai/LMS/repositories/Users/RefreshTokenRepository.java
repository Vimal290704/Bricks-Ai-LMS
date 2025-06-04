package com.bricks_ai_lms.bricks.ai.lms.repositories.Users;

import com.bricks_ai_lms.bricks.ai.lms.entities.UserEnt.RefreshToken;
import com.bricks_ai_lms.bricks.ai.lms.entities.UserEnt.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByTokenAndIsActiveTrue(String token);

    @Modifying
    @Transactional
    @Query("UPDATE RefreshToken rt SET rt.isActive = false WHERE rt.username = :username")
    void deactivateAllTokensForUser(@Param("username") String username);

    @Modifying
    @Transactional
    @Query("UPDATE RefreshToken rt SET rt.isActive = false WHERE rt.token = :token")
    void deactivateToken(@Param("token") String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshToken rt WHERE rt.expiryDate < :now OR rt.isActive = false")
    void deleteExpiredAndInactiveTokens(@Param("now") LocalDateTime now);

    @Query("SELECT COUNT(rt) FROM RefreshToken rt WHERE rt.username = :username AND rt.isActive = true")
    long countActiveTokensForUser(@Param("username") String username);
}
