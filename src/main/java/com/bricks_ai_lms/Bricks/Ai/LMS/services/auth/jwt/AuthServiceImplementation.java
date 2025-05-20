package com.bricks_ai_lms.Bricks.Ai.LMS.services.auth.jwt;


import com.bricks_ai_lms.Bricks.Ai.LMS.dtos.SignupRequest;
import com.bricks_ai_lms.Bricks.Ai.LMS.dtos.UserDto;
import com.bricks_ai_lms.Bricks.Ai.LMS.entities.User;
import com.bricks_ai_lms.Bricks.Ai.LMS.enums.UserRole;
import com.bricks_ai_lms.Bricks.Ai.LMS.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImplementation implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(SignupRequest signupRequest){
        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setRole(UserRole.STUDENT);
        User createdUser = userRepository.save(user);
        UserDto createduserDto = new UserDto();
        createduserDto.setId(createdUser.getId());
        createduserDto.setName(createdUser.getName());
        createduserDto.setEmail(createdUser.getEmail());
        createduserDto.setRole(createdUser.getRole());
        createduserDto.setPassword(createdUser.getPassword());
        return createduserDto;
    }
}