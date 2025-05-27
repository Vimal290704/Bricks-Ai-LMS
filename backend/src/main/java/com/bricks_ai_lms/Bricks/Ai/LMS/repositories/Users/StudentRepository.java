package com.bricks_ai_lms.Bricks.Ai.LMS.repositories.Users;

import com.bricks_ai_lms.Bricks.Ai.LMS.entities.UserEnt.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student , Long> {

}
