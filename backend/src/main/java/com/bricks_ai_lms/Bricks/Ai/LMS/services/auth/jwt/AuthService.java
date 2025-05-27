package com.bricks_ai_lms.bricks.ai.lms.services.auth.jwt;


import com.bricks_ai_lms.bricks.ai.lms.dtos.SignUp.SignupRequest;
import com.bricks_ai_lms.bricks.ai.lms.dtos.Users.UserDto;

public interface AuthService {


    UserDto createUser(SignupRequest signupRequest);
}