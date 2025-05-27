package com.bricks_ai_lms.bricks.ai.lms.dtos.Users;

import com.bricks_ai_lms.bricks.ai.lms.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateRequestDto {

    private String name;

    private String email;

    private Gender gender;

    private String phone;

    private LocalDate dateOfBirth;

    private Integer height;

    private Integer weight;

    private String bloodGroup;

    private String address;

    private String city;

    private String state;

    private String pinCode;

    private String profileImageUrl;
}
