package com.bricks_ai_lms.bricks.ai.lms.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Long classId;
}

