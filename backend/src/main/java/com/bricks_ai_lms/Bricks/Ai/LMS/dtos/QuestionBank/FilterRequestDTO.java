package com.bricks_ai_lms.Bricks.Ai.LMS.dtos.QuestionBank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterRequestDTO {
    private Integer topicId;
    private String difficulty;
    private Integer subjectId;
    private String type;
}
