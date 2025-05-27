package com.bricks_ai_lms.bricks.ai.lms.dtos.Authentication;

import com.bricks_ai_lms.bricks.ai.lms.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {

    private String jwt;

    private UserRole userRole;

    private Long userId;

}
