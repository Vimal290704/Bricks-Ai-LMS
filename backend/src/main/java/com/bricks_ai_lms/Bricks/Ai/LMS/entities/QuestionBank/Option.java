package com.bricks_ai_lms.bricks.ai.lms.entities.QuestionBank;


import com.bricks_ai_lms.bricks.ai.lms.enums.QuestionBank.OptionLabel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "options")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    @JsonIgnore
    private Question question;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OptionLabel label;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;


}
