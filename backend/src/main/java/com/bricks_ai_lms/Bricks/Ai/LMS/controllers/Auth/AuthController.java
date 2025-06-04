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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
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
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            String accessToken = jwtUtil.generateAccessToken(userDetails);
            RefreshToken refreshTokenEntity = refreshTokenService.createRefreshToken(userDetails);
            Date accessTokenExpiry = jwtUtil.getExpirationDate(accessToken);
            Date refreshTokenExpiry = jwtUtil.getExpirationDate(refreshTokenEntity.getToken());
            AuthenticationResponse authenticationResponse = new AuthenticationResponse(
                    accessToken,
                    refreshTokenEntity.getToken(),
                    accessTokenExpiry,
                    refreshTokenExpiry,
                    userDetails.getUsername()
            );
            refreshTokenService.cleanupExpiredTokens();
            return ResponseEntity.ok(authenticationResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Invalid credentials"));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        try {
            String refreshToken = request.getRefreshToken();
            String username = jwtUtil.extractUsername(refreshToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (refreshTokenService.validateRefreshToken(refreshToken, userDetails)) {
                refreshTokenService.deactivateToken(refreshToken);
                String newRefreshToken = refreshTokenService.createRefreshToken(userDetails).getToken();
                String newAccessToken = jwtUtil.generateAccessToken(userDetails);
                Date accessTokenExpiry = jwtUtil.getExpirationDate(newAccessToken);

                RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse(newAccessToken, newRefreshToken, accessTokenExpiry);
                return ResponseEntity.ok(refreshTokenResponse);
            } else {
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse("Invalid refresh token"));
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Token refresh failed"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader,
                                    @RequestBody(required = false) LogoutRequest logoutRequest) {
        try {
            String accessToken = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                accessToken = authHeader.substring(7);
            }

            if (accessToken != null) {
                String username = jwtUtil.extractUsername(accessToken);

                if (logoutRequest != null && logoutRequest.getRefreshToken() != null) {
                    refreshTokenService.deactivateToken(logoutRequest.getRefreshToken());
                } else {
                    refreshTokenService.deactivateAllTokensForUser(username);
                }

            }
            refreshTokenService.cleanupExpiredTokens();
            return ResponseEntity.ok("Logout successful");

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Logout failed"));
        }
    }
}