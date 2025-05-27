package com.bricks_ai_lms.bricks.ai.lms.dtos.QuestionBank;

import com.bricks_ai_lms.bricks.ai.lms.enums.QuestionBank.DifficultyLevel;
import com.bricks_ai_lms.bricks.ai.lms.enums.QuestionBank.QuestionType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class QuestionDTO {
    private Integer id;

    private String text;

    private QuestionType type;

    private DifficultyLevel difficulty;

    private String correctAnswer;

    private String explanation;

    private Integer year;

    private Integer topicId;

    private String topicName;

    private Integer subjectId;

    private String subjectName;

    private Integer sourceId;

    private String sourceName;

    private List<OptionDTO> options;

}
