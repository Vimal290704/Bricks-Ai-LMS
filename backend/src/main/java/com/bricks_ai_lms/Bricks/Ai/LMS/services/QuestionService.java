package com.bricks_ai_lms.Bricks.Ai.LMS.services;

import com.bricks_ai_lms.Bricks.Ai.LMS.dtos.QuestionBankDTOs.OptionDTO;
import com.bricks_ai_lms.Bricks.Ai.LMS.dtos.QuestionBankDTOs.QuestionDTO;
import com.bricks_ai_lms.Bricks.Ai.LMS.entities.QuestionBank.Option;
import com.bricks_ai_lms.Bricks.Ai.LMS.entities.QuestionBank.Question;
import com.bricks_ai_lms.Bricks.Ai.LMS.enums.QuestionBank.DifficultyLevel;
import com.bricks_ai_lms.Bricks.Ai.LMS.enums.QuestionBank.QuestionType;
import com.bricks_ai_lms.Bricks.Ai.LMS.repositories.QuestionBank.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<QuestionDTO> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public QuestionDTO getQuestionById(Integer id) {
        Optional<Question> questionOpt = questionRepository.findById(id);
        return questionOpt.map(this::convertToDTO).orElse(null);
    }

    public List<QuestionDTO> getQuestionsByTopic(Integer topicId) {
        List<Question> questions = questionRepository.findByTopicId(topicId);
        return questions.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<QuestionDTO> getQuestionsBySubject(Integer subjectId) {
        List<Question> questions = questionRepository.findBySubjectId(subjectId);
        return questions.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<QuestionDTO> getQuestionsByDifficulty(String difficultyStr) {
        DifficultyLevel difficulty = DifficultyLevel.valueOf(difficultyStr);
        List<Question> questions = questionRepository.findByDifficulty(difficulty);
        return questions.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<QuestionDTO> getQuestionsByType(String typeStr) {
        QuestionType type = QuestionType.valueOf(typeStr);
        List<Question> questions = questionRepository.findByType(type);
        return questions.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<QuestionDTO> getQuestionsBySource(Integer sourceId) {
        List<Question> questions = questionRepository.findBySourceId(sourceId);
        return questions.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<QuestionDTO> getQuestionsByYear(Integer year) {
        List<Question> questions = questionRepository.findByYear(year);
        return questions.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<QuestionDTO> getQuestionsByTopicAndDifficulty(Integer topicId, String difficultyStr) {
        DifficultyLevel difficulty = DifficultyLevel.valueOf(difficultyStr);
        List<Question> questions = questionRepository.findByTopicIdAndDifficulty(topicId, difficulty);
        return questions.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<QuestionDTO> filterQuestions(Integer topicId, String difficultyStr, Integer subjectId, String typeStr) {
        DifficultyLevel difficulty = null;
        if (difficultyStr != null && !difficultyStr.isEmpty() && !difficultyStr.equalsIgnoreCase("ALL")) {
            difficulty = DifficultyLevel.valueOf(difficultyStr);
        }

        QuestionType type = null;
        if (typeStr != null && !typeStr.isEmpty() && !typeStr.equalsIgnoreCase("ALL")) {
            type = QuestionType.valueOf(typeStr);
        }

        Integer finalTopicId = (topicId != null && topicId > 0) ? topicId : null;
        Integer finalSubjectId = (subjectId != null && subjectId > 0) ? subjectId : null;

        List<Question> questions = questionRepository.filterQuestions(finalTopicId, difficulty, finalSubjectId, type);
        return questions.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private QuestionDTO convertToDTO(Question question) {
        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setText(question.getText());
        dto.setType(question.getType());
        dto.setDifficulty(question.getDifficulty());
        dto.setCorrectAnswer(question.getCorrectAnswer());
        dto.setExplanation(question.getExplanation());
        dto.setYear(question.getYear());

        if (question.getTopic() != null) {
            dto.setTopicId(question.getTopic().getId());
            dto.setTopicName(question.getTopic().getName());

            if (question.getTopic().getSubject() != null) {
                dto.setSubjectId(question.getTopic().getSubject().getId());
                dto.setSubjectName(question.getTopic().getSubject().getName());
            }
        }

        if (question.getSource() != null) {
            dto.setSourceId(question.getSource().getId());
            dto.setSourceName(question.getSource().getName());
        }

        if (question.getOptions() != null) {
            List<OptionDTO> optionDTOs = question.getOptions().stream().map(this::convertToOptionDTO).collect(Collectors.toList());
            dto.setOptions(optionDTOs);
        }

        return dto;
    }

    private OptionDTO convertToOptionDTO(Option option) {
        OptionDTO dto = new OptionDTO();
        dto.setId(option.getId());
        dto.setLabel(option.getLabel());
        dto.setText(option.getText());
        return dto;
    }
}
