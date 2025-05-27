package com.bricks_ai_lms.bricks.ai.lms.repositories.Users;
import com.bricks_ai_lms.bricks.ai.lms.entities.UserEnt.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher , Long> {
}
