package com.bricks_ai_lms.Bricks.Ai.LMS.dtos.Users;

import com.bricks_ai_lms.Bricks.Ai.LMS.enums.Gender;
import com.bricks_ai_lms.Bricks.Ai.LMS.enums.UserRole;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {

    private Long id;

    private String username;

    private String name;

    private String email;

    private UserRole role;

    private Gender gender;

    private String phone;

    private String school;

    private LocalDate dateOfBirth;

    private Integer height;

    private Integer weight;

    private String bloodGroup;

    private Boolean isActive;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private String password;

}