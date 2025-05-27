package com.bricks_ai_lms.Bricks.Ai.LMS.dtos.Authentication;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationRequest {

    private String email;

    private String password;

    @Override
    public String toString() {
        return "AuthenticationRequest{" +
                "email='" + email + '\'' +
                ", password='[PROTECTED]'" +
                '}';
    }

}
