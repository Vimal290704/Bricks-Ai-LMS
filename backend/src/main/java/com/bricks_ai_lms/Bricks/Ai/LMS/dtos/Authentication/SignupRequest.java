package com.bricks_ai_lms.bricks.ai.lms.dtos.Authentication;

import com.bricks_ai_lms.bricks.ai.lms.enums.User.Gender;
import com.bricks_ai_lms.bricks.ai.lms.enums.User.UserRole;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SignupRequest {
    private String username;
    private String name;
    private String email;
    private String password;
    private UserRole role;
    private Gender gender;
    private String phone;
    private String school;
    private LocalDate dateOfBirth;
    private Integer height;
    private Integer weight;
    private String bloodGroup;
}