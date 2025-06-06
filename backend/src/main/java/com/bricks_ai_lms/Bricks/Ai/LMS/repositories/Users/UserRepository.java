package com.bricks_ai_lms.bricks.ai.lms.repositories.Users;

import com.bricks_ai_lms.bricks.ai.lms.entities.UserEnt.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

}
