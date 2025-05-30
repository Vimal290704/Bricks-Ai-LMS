package com.bricks_ai_lms.bricks.ai.lms.dtos.Announcement;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementRequestDTO {
    private String title;
    private String description;
    private Long subjectId;
}
