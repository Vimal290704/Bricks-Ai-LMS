package com.bricks_ai_lms.bricks.ai.lms.dtos.Authentication;

import lombok.Data;

import java.util.Date;

@Data
public class AuthenticationResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Date accessTokenExpiryDate;
    private Date refreshTokenExpiryDate;
    private String username;

    public AuthenticationResponse(String accessToken, String refreshToken,
                                  Date accessTokenExpiryDate, Date refreshTokenExpiryDate,
                                  String username) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpiryDate = accessTokenExpiryDate;
        this.refreshTokenExpiryDate = refreshTokenExpiryDate;
        this.username = username;
    }
}
