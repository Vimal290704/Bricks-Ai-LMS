package com.bricks_ai_lms.Bricks.Ai.LMS.controllers;

import com.bricks_ai_lms.Bricks.Ai.LMS.dtos.Authentication.AuthenticationRequest;
import com.bricks_ai_lms.Bricks.Ai.LMS.dtos.Authentication.AuthenticationResponse;
import com.bricks_ai_lms.Bricks.Ai.LMS.dtos.SignUp.SignupRequest;
import com.bricks_ai_lms.Bricks.Ai.LMS.dtos.Users.UserDto;
import com.bricks_ai_lms.Bricks.Ai.LMS.entities.UserEnt.User;
import com.bricks_ai_lms.Bricks.Ai.LMS.repositories.Users.UserRepository;
import com.bricks_ai_lms.Bricks.Ai.LMS.services.auth.jwt.AuthService;
import com.bricks_ai_lms.Bricks.Ai.LMS.services.auth.jwt.UserDetailsServiceImpl;
import com.bricks_ai_lms.Bricks.Ai.LMS.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsServiceImpl userDetailsService;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, UserRepository userRepository, JwtUtil jwtUtil) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {

        UserDto createdUserDto = authService.createUser(signupRequest);

        if (createdUserDto == null) {
            return new ResponseEntity<>("Failed to create user!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws IOException {
        System.out.println("Login endpoint hit with email: " + authenticationRequest.getEmail() + authenticationRequest.getPassword());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean matches = encoder.matches("defaultPassword123", "$2a$10$N.zmdr9k7uOcNfTaewVAsO4/0rXDbqjmHamm8RfChUHVkdEzh7mTu");
        System.out.println("Password matches: " + matches);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or Password");
        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not active");
            return null;
        }
        System.out.println("Passed");
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        Optional<User> optionalUser = userRepository.findByEmail(userDetails.getUsername());
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserRole(optionalUser.get().getRole());
            authenticationResponse.setUserId(optionalUser.get().getId());
        }
        return authenticationResponse;
    }
}