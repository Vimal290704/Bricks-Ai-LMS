package com.bricks_ai_lms.bricks.ai.lms.dtos.Authentication;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutRequest {
    private String refreshToken;
}
