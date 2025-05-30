package com.bricks_ai_lms.bricks.ai.lms.services.auth.jwt;


import com.bricks_ai_lms.bricks.ai.lms.dtos.Authentication.SignupRequest;
import com.bricks_ai_lms.bricks.ai.lms.dtos.Users.UserDto;
import com.bricks_ai_lms.bricks.ai.lms.entities.UserEnt.User;
import com.bricks_ai_lms.bricks.ai.lms.enums.User.UserRole;
import com.bricks_ai_lms.bricks.ai.lms.repositories.Users.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImplementation implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final UserDto createdUserDto = new UserDto();
    @Override
    @Transactional
    public UserDto createUser(SignupRequest signupRequest) {
        // Check if email or username already exists
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }
        if (userRepository.findByUsername(signupRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Username already in use");
        }
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setRole(signupRequest.getRole() != null ? signupRequest.getRole() : UserRole.STUDENT);
        user.setGender(signupRequest.getGender());
        user.setPhone(signupRequest.getPhone());
        user.setSchool(signupRequest.getSchool());
        user.setDateOfBirth(signupRequest.getDateOfBirth());
        user.setHeight(signupRequest.getHeight());
        user.setWeight(signupRequest.getWeight());
        user.setBloodGroup(signupRequest.getBloodGroup());
        User createdUser = userRepository.save(user);
        createdUserDto.setId(createdUser.getId());
        createdUserDto.setUsername(createdUser.getUsername());
        createdUserDto.setName(createdUser.getName());
        createdUserDto.setEmail(createdUser.getEmail());
        createdUserDto.setRole(createdUser.getRole());
        createdUserDto.setGender(createdUser.getGender());
        createdUserDto.setPhone(createdUser.getPhone());
        createdUserDto.setSchool(createdUser.getSchool());
        createdUserDto.setDateOfBirth(createdUser.getDateOfBirth());
        createdUserDto.setHeight(createdUser.getHeight());
        createdUserDto.setWeight(createdUser.getWeight());
        createdUserDto.setBloodGroup(createdUser.getBloodGroup());
        return createdUserDto;
    }
}