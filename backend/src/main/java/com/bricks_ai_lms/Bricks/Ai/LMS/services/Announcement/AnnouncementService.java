package com.bricks_ai_lms.bricks.ai.lms.services.Announcement;

import com.bricks_ai_lms.bricks.ai.lms.dtos.Announcement.AnnouncementRequestDTO;
import com.bricks_ai_lms.bricks.ai.lms.dtos.Announcement.AnnouncementResponseDTO;

import java.util.List;

public interface AnnouncementService {
    AnnouncementResponseDTO create(AnnouncementRequestDTO request);
    List<AnnouncementResponseDTO> getAll();
    AnnouncementResponseDTO getById(Long id);
    AnnouncementResponseDTO update(Long id, AnnouncementRequestDTO request);
    void delete(Long id);
}

