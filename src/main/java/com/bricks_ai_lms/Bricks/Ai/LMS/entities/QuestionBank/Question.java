package com.bricks_ai_lms.Bricks.Ai.LMS.entities.QuestionBank;


import com.bricks_ai_lms.Bricks.Ai.LMS.enums.QuestionBank.DifficultyLevel;
import com.bricks_ai_lms.Bricks.Ai.LMS.enums.QuestionBank.QuestionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn(name="topic_id" , nullable = false)
    @JsonIgnore
    private Topic topic;

    @Column(nullable = false , columnDefinition = "TEXT")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DifficultyLevel difficulty;

    @Column(name="correct_answer" , columnDefinition = "TEXT")
    private String correctAnswer;

    @Column(columnDefinition = "TEXT")
    private String explanation;

    @ManyToOne
    @JoinColumn(name="source_id")
    private Source source;

    private Integer year;

    @OneToMany(mappedBy = "question" , cascade = CascadeType.ALL)
    private List<Option> options;


}
