package com.bricks_ai_lms.Bricks.Ai.LMS.repositories;

import com.bricks_ai_lms.Bricks.Ai.LMS.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}