package com.bricks_ai_lms.Bricks.Ai.LMS.entities.QuestionBank;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Topic> topics;

    private String description;

}
