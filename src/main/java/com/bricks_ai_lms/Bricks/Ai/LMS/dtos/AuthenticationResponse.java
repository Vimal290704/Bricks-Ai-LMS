package com.bricks_ai_lms.Bricks.Ai.LMS.dtos;

import com.bricks_ai_lms.Bricks.Ai.LMS.enums.UserRole;

import lombok.Data;

@Data
public class AuthenticationResponse {

    private String jwt;

    private UserRole userRole;

    private Long userId;


}
