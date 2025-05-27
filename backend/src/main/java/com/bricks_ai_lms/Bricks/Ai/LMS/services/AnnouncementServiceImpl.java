package com.bricks_ai_lms.bricks.ai.lms.services;

import com.bricks_ai_lms.bricks.ai.lms.dtos.AnnouncementRequestDTO;
import com.bricks_ai_lms.bricks.ai.lms.dtos.AnnouncementResponseDTO;
import com.bricks_ai_lms.bricks.ai.lms.entities.Announcement;
import com.bricks_ai_lms.bricks.ai.lms.repositories.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementRepository repository;

    @Override
    public AnnouncementResponseDTO create(AnnouncementRequestDTO request) {
        Announcement announcement = new Announcement(null, request.getTitle(), request.getDescription(), request.getClassId());
        Announcement saved = repository.save(announcement);
        return mapToResponse(saved);
    }

    @Override
    public List<AnnouncementResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AnnouncementResponseDTO getById(Long id) {
        Announcement announcement = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Announcement not found"));
        return mapToResponse(announcement);
    }

    @Override
    public AnnouncementResponseDTO update(Long id, AnnouncementRequestDTO request) {
        Announcement announcement = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Announcement not found"));
        announcement.setTitle(request.getTitle());
        announcement.setDescription(request.getDescription());
        announcement.setClassId(request.getClassId());
        return mapToResponse(repository.save(announcement));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private AnnouncementResponseDTO mapToResponse(Announcement announcement) {
        return new AnnouncementResponseDTO(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getDescription(),
                announcement.getClassId()
        );
    }
}
