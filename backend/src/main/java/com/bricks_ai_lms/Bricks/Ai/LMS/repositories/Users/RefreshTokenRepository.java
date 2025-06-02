package com.bricks_ai_lms.bricks.ai.lms.repositories.Users;

import com.bricks_ai_lms.bricks.ai.lms.entities.UserEnt.RefreshToken;
import com.bricks_ai_lms.bricks.ai.lms.entities.UserEnt.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(User user);

    void deleteByUser(User user);
}
