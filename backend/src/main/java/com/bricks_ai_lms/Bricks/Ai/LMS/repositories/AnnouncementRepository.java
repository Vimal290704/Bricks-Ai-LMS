package com.bricks_ai_lms.bricks.ai.lms.repositories;

import com.bricks_ai_lms.bricks.ai.lms.entities.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    // Optional: Add custom queries if needed
}
