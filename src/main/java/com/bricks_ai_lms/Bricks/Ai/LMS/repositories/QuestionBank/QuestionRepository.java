package com.bricks_ai_lms.Bricks.Ai.LMS.repositories.QuestionBank;

import com.bricks_ai_lms.Bricks.Ai.LMS.entities.QuestionBank.Question;
import com.bricks_ai_lms.Bricks.Ai.LMS.enums.QuestionBank.DifficultyLevel;
import com.bricks_ai_lms.Bricks.Ai.LMS.enums.QuestionBank.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByTopicId(Integer topicId);

    List<Question> findByDifficulty(DifficultyLevel difficulty);

    List<Question> findByType(QuestionType type);

    List<Question> findBySourceId(Integer sourceId);

    List<Question> findByYear(Integer year);

    @Query("SELECT q FROM Question q WHERE q.topic.id = :topicId AND q.difficulty = :difficulty")
    List<Question> findByTopicIdAndDifficulty(@Param("topicId") Integer topicId, @Param("difficulty") DifficultyLevel difficulty);

    @Query("SELECT q FROM Question q WHERE q.topic.subject.id = :subjectId")
    List<Question> findBySubjectId(@Param("subjectId") Integer subjectId);
}
