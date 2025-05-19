package com.bricks_ai_lms.Bricks.Ai.LMS.controllers;

import com.bricks_ai_lms.Bricks.Ai.LMS.dtos.SignupRequest;
import com.bricks_ai_lms.Bricks.Ai.LMS.dtos.UserDto;
import com.bricks_ai_lms.Bricks.Ai.LMS.services.auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {

        UserDto createdUserDto = authService.createUser(signupRequest);

        if (createdUserDto == null) {
            return new ResponseEntity<>("Failed to create user!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }
}