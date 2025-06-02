package com.bricks_ai_lms.bricks.ai.lms.controllers.Auth;

import com.bricks_ai_lms.bricks.ai.lms.Exceptions.RefreshTokenException;
import com.bricks_ai_lms.bricks.ai.lms.dtos.Authentication.*;
import com.bricks_ai_lms.bricks.ai.lms.dtos.Errors.ErrorResponse;
import com.bricks_ai_lms.bricks.ai.lms.dtos.Users.UserDto;
import com.bricks_ai_lms.bricks.ai.lms.entities.UserEnt.RefreshToken;
import com.bricks_ai_lms.bricks.ai.lms.entities.UserEnt.User;
import com.bricks_ai_lms.bricks.ai.lms.repositories.Users.UserRepository;
import com.bricks_ai_lms.bricks.ai.lms.services.auth.jwt.AuthService;
import com.bricks_ai_lms.bricks.ai.lms.services.auth.jwt.RefreshTokenService;
import com.bricks_ai_lms.bricks.ai.lms.services.auth.jwt.UserDetailsServiceImpl;
import com.bricks_ai_lms.bricks.ai.lms.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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

    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, UserRepository userRepository, JwtUtil jwtUtil, RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
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
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or Password");
        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not active");
            return null;
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String accessToken = jwtUtil.generateAccessToken(userDetails);
        Optional<User> optionalUser = userRepository.findByEmail(userDetails.getUsername());
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setUsername(userDetails.getUsername());
        if (optionalUser.isPresent()) {
            authenticationResponse.setAccessToken(accessToken);
            authenticationResponse.setRefreshToken(refreshTokenService.createRefreshToken(optionalUser.get().getId()).getToken());
        }
        return authenticationResponse;
    }

    @PostMapping("/refresh")
    private ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
            String refreshToken = refreshTokenRequest.getRefreshToken();

            if (refreshToken == null || refreshToken.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Refresh token cannot be empty"));
            }

            RefreshToken existingRefreshToken = refreshTokenService.findByToken(refreshToken)
                    .orElseThrow(() -> new RuntimeException("No token exist in database"));

            RefreshToken validToken = refreshTokenService.verifyExpiration(existingRefreshToken);

            User user = validToken.getUser();

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

            String newAccessToken = jwtUtil.generateAccessToken(userDetails);
            RefreshToken newRefreshToken = refreshTokenService.rotateRefreshToken(validToken);


            RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse();
            refreshTokenResponse.setRefreshToken(newRefreshToken.getToken());
            refreshTokenResponse.setAccessToken(newAccessToken);
            return ResponseEntity.ok(refreshTokenResponse);
        } catch (RefreshTokenException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error during token refresh"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequest request) {
        refreshTokenService.deleteByEmail(request.getEmail());
        return ResponseEntity.ok("Logout successful");
    }
}