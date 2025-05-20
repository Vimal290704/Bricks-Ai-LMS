package com.bricks_ai_lms.Bricks.Ai.LMS.dtos;

import com.bricks_ai_lms.Bricks.Ai.LMS.enums.Gender;
import com.bricks_ai_lms.Bricks.Ai.LMS.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String name;

    private String email;

    private String password;

    private UserRole Role;

    private Gender gender;

    private String phone;

}