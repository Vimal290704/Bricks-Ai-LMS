package com.bricks_ai_lms.Bricks.Ai.LMS.repositories.QuestionBank;

import com.bricks_ai_lms.Bricks.Ai.LMS.entities.QuestionBank.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
}
