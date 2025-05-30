package com.bricks_ai_lms.bricks.ai.lms.entities.Announcement;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "announcements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Column(name = "subject_id")
    private Long subjectId;
}
