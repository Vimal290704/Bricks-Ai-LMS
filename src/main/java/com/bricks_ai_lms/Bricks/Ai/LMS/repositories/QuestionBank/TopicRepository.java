package com.bricks_ai_lms.Bricks.Ai.LMS.repositories.QuestionBank;

import com.bricks_ai_lms.Bricks.Ai.LMS.entities.QuestionBank.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {
    List<Topic> findBySubjectId(Integer subjectId);
}
