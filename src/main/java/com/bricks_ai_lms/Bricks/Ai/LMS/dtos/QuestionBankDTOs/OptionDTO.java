package com.bricks_ai_lms.Bricks.Ai.LMS.dtos.QuestionBankDTOs;

import com.bricks_ai_lms.Bricks.Ai.LMS.enums.QuestionBank.OptionLabel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OptionDTO{

    private Integer id;

    private OptionLabel label;

    private String text;

}
