package com.bricks_ai_lms.Bricks.Ai.LMS.repositories;

import com.bricks_ai_lms.Bricks.Ai.LMS.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByEmail(String email);
}