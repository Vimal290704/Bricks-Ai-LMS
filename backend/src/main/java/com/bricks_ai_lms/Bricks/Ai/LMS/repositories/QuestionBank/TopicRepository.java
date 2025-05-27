package com.bricks_ai_lms.bricks.ai.lms.repositories.QuestionBank;

import com.bricks_ai_lms.bricks.ai.lms.entities.QuestionBank.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {
    List<Topic> findBySubjectId(Integer subjectId);
}
