package com.bricks_ai_lms.bricks.ai.lms.repositories.QuestionBank;

import com.bricks_ai_lms.bricks.ai.lms.entities.QuestionBank.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<Option, Integer> {
    List<Option> findByQuestionId(Integer questionId);
}
