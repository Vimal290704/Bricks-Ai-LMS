package com.bricks_ai_lms.bricks.ai.lms.dtos.Users;

import com.bricks_ai_lms.bricks.ai.lms.enums.UserRole;
import lombok.Data;

@Data
public class UserDetailResponseDto {
    private UserRole role;

    private Object userDetails;

    private String message;

    private Boolean success;

    public UserDetailResponseDto(UserRole role, Object userDetails) {
        this.role = role;
        this.userDetails = userDetails;
        this.success = true;
        this.message = "User Detail retrieved successfully";
    }

    public UserDetailResponseDto(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

}
