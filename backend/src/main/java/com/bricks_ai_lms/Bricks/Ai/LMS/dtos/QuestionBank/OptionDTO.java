package com.bricks_ai_lms.bricks.ai.lms.dtos.QuestionBank;

import com.bricks_ai_lms.bricks.ai.lms.enums.QuestionBank.OptionLabel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OptionDTO {

    private Integer id;

    private OptionLabel label;

    private String text;

}
