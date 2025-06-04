package com.bricks_ai_lms.bricks.ai.lms.dtos.Authentication;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RefreshTokenResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Date accessTokenExpiryDate;

    public RefreshTokenResponse(String accessToken,String refreshToken ,Date accessTokenExpiryDate) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.accessTokenExpiryDate = accessTokenExpiryDate;
    }
}
