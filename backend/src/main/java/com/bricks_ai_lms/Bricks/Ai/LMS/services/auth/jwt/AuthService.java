package com.bricks_ai_lms.Bricks.Ai.LMS.services.auth.jwt;


import com.bricks_ai_lms.Bricks.Ai.LMS.dtos.SignupRequest;
import com.bricks_ai_lms.Bricks.Ai.LMS.dtos.UserDto;

public interface AuthService {


    UserDto createUser(SignupRequest signupRequest);
}