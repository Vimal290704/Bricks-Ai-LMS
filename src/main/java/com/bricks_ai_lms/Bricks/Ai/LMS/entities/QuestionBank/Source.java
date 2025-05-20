package com.bricks_ai_lms.Bricks.Ai.LMS.entities.QuestionBank;


import com.bricks_ai_lms.Bricks.Ai.LMS.enums.QuestionBank.SourceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@Entity
@Table(name = "sources")
public class Source {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SourceType type;


    private String details;

    @OneToMany(mappedBy = "source")
    private List<Question> questions;


}
