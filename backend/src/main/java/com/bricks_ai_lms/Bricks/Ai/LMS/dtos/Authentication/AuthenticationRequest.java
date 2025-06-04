package com.bricks_ai_lms.bricks.ai.lms.dtos.Authentication;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationRequest {

    private String username;

    private String password;

    @Override
    public String toString() {
        return "AuthenticationRequest{" +
                "email='" + username + '\'' +
                ", password='[PROTECTED]'" +
                '}';
    }

}
