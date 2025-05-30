package com.bricks_ai_lms.bricks.ai.lms.controllers.Announcement;

import com.bricks_ai_lms.bricks.ai.lms.dtos.Announcement.AnnouncementRequestDTO;
import com.bricks_ai_lms.bricks.ai.lms.dtos.Announcement.AnnouncementResponseDTO;
import com.bricks_ai_lms.bricks.ai.lms.services.Announcement.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementService service;

    @PostMapping
    public ResponseEntity<AnnouncementResponseDTO> create(@RequestBody AnnouncementRequestDTO request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping
    public ResponseEntity<List<AnnouncementResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnnouncementResponseDTO> update(@PathVariable Long id, @RequestBody AnnouncementRequestDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

