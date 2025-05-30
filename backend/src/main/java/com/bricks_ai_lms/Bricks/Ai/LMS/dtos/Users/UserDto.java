package com.bricks_ai_lms.bricks.ai.lms.dtos.Users;

import com.bricks_ai_lms.bricks.ai.lms.enums.User.Gender;
import com.bricks_ai_lms.bricks.ai.lms.enums.User.UserRole;
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