package com.bricks_ai_lms.Bricks.Ai.LMS.controllers;

import com.bricks_ai_lms.Bricks.Ai.LMS.dtos.Users.UserDetailResponseDto;
import com.bricks_ai_lms.Bricks.Ai.LMS.dtos.Users.UserDto;
import com.bricks_ai_lms.Bricks.Ai.LMS.dtos.Users.UserUpdateRequestDto;
import com.bricks_ai_lms.Bricks.Ai.LMS.services.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-details")
@CrossOrigin(origins = "*")
public class UserDetailController {

    @Autowired
    private UserDetailService userDetailService;

    @GetMapping("/profile")
    public ResponseEntity<UserDetailResponseDto> getUserProfile(Authentication authentication) {
        try {
            String username = authentication.getName();
            UserDetailResponseDto userDetails = userDetailService.getUserDetailsByUsername(username);
            return ResponseEntity.ok(userDetails);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new UserDetailResponseDto("Error fetching user details: " + e.getMessage(), false));
        }
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserDetailResponseDto> getUserProfileById(
            @PathVariable Long userId,
            Authentication authentication) {
        try {
            UserDetailResponseDto userDetails = userDetailService.getUserDetailsById(userId, authentication.getName());
            return ResponseEntity.ok(userDetails);
        } catch (SecurityException e) {
            return ResponseEntity.status(403)
                    .body(new UserDetailResponseDto("Access denied: " + e.getMessage(), false));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new UserDetailResponseDto("Error fetching user details: " + e.getMessage(), false));
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<UserDetailResponseDto> updateUserProfile(
            @RequestBody UserUpdateRequestDto updateRequest,
            Authentication authentication) {
        try {
            String username = authentication.getName();
            UserDetailResponseDto updatedUser = userDetailService.updateUserProfile(username, updateRequest);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new UserDetailResponseDto("Error updating profile: " + e.getMessage(), false));
        }
    }

    @GetMapping("/basic-info")
    public ResponseEntity<UserDto> getBasicUserInfo(Authentication authentication) {
        try {
            String username = authentication.getName();
            UserDto basicInfo = userDetailService.getBasicUserInfo(username);
            return ResponseEntity.ok(basicInfo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}