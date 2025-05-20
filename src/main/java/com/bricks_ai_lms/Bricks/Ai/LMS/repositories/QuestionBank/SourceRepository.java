package com.bricks_ai_lms.Bricks.Ai.LMS.repositories.QuestionBank;

import com.bricks_ai_lms.Bricks.Ai.LMS.entities.QuestionBank.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceRepository extends JpaRepository<Source, Integer> {
}