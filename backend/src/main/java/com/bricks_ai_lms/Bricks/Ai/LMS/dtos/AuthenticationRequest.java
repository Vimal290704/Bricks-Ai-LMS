package com.bricks_ai_lms.Bricks.Ai.LMS.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationRequest {

    private String email;

    private String password;

}
