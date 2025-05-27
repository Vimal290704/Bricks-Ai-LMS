package com.bricks_ai_lms.Bricks.Ai.LMS.entities.UserEnt;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student_classes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grade", nullable = false)
    private String grade;

    @Column(name = "section", nullable = false)
    private String section;

    public String getDisplayName() {
        return grade + "-" + section;
    }
}
