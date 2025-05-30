package com.bricks_ai_lms.bricks.ai.lms.controllers.Question;

import com.bricks_ai_lms.bricks.ai.lms.dtos.QuestionBank.FilterRequestDTO;
import com.bricks_ai_lms.bricks.ai.lms.dtos.QuestionBank.QuestionDTO;
import com.bricks_ai_lms.bricks.ai.lms.services.Question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        List<QuestionDTO> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable Integer id) {
        QuestionDTO question = questionService.getQuestionById(id);
        if (question == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(question);
    }

    @GetMapping("/topic/{topicId}")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByTopic(@PathVariable Integer topicId) {
        List<QuestionDTO> questions = questionService.getQuestionsByTopic(topicId);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<QuestionDTO>> getQuestionsBySubject(@PathVariable Integer subjectId) {
        List<QuestionDTO> questions = questionService.getQuestionsBySubject(subjectId);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByDifficulty(@PathVariable String difficulty) {
        List<QuestionDTO> questions = questionService.getQuestionsByDifficulty(difficulty);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByType(@PathVariable String type) {
        List<QuestionDTO> questions = questionService.getQuestionsByType(type);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/source/{sourceId}")
    public ResponseEntity<List<QuestionDTO>> getQuestionsBySource(@PathVariable Integer sourceId) {
        List<QuestionDTO> questions = questionService.getQuestionsBySource(sourceId);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByYear(@PathVariable Integer year) {
        List<QuestionDTO> questions = questionService.getQuestionsByYear(year);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/topic/{topicId}/difficulty/{difficulty}")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByTopicAndDifficulty(
            @PathVariable Integer topicId,
            @PathVariable String difficulty) {
        List<QuestionDTO> questions = questionService.getQuestionsByTopicAndDifficulty(topicId, difficulty);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<QuestionDTO>> filterQuestions(
            @RequestParam(required = false) Integer topicId,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) Integer subjectId,
            @RequestParam(required = false) String type) {

        List<QuestionDTO> questions = questionService.filterQuestions(topicId, difficulty, subjectId, type);
        return ResponseEntity.ok(questions);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<QuestionDTO>> filterQuestionsPost(@RequestBody FilterRequestDTO filterRequest) {
        return ResponseEntity.ok(questionService.filterQuestions(
                filterRequest.getTopicId(),
                filterRequest.getDifficulty(),
                filterRequest.getSubjectId(),
                filterRequest.getType()
        ));
    }
}
