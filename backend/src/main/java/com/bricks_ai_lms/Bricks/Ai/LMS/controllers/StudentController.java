package com.bricks_ai_lms.bricks.ai.lms.controllers;

import com.bricks_ai_lms.bricks.ai.lms.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/create-dummy")
    public String createDummyStudent() {
        studentService.createStudent();
        return "Student created successfully";
    }
}
