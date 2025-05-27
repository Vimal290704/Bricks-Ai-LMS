package com.bricks_ai_lms.bricks.ai.lms.repositories.QuestionBank;

import com.bricks_ai_lms.bricks.ai.lms.entities.QuestionBank.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
}
