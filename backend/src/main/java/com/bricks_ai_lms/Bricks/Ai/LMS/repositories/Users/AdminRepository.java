package com.bricks_ai_lms.Bricks.Ai.LMS.repositories.Users;

import com.bricks_ai_lms.Bricks.Ai.LMS.entities.UserEnt.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

}
