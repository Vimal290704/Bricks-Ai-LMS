package com.bricks_ai_lms.Bricks.Ai.LMS.services.auth.jwt;


import com.bricks_ai_lms.Bricks.Ai.LMS.dtos.SignUp.SignupRequest;
import com.bricks_ai_lms.Bricks.Ai.LMS.dtos.Users.UserDto;
import com.bricks_ai_lms.Bricks.Ai.LMS.entities.UserEnt.User;
import com.bricks_ai_lms.Bricks.Ai.LMS.enums.UserRole;
import com.bricks_ai_lms.Bricks.Ai.LMS.repositories.Users.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImplementation implements AuthService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public AuthServiceImplementation(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
        UserDto createdUserDto = new UserDto();
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