package com.bricks_ai_lms.Bricks.Ai.LMS.dtos.Users;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminDto extends UserDto {

    private String adminId;

    private String employeeId;

    private String designation;

    private String department;

    private LocalDate joiningDate;

    private String address;

    private String city;

    private String state;

    private String pinCode;

    private List<String> permissions;

    private String profileImageUrl;

    private LocalDate lastLoginDate;

    private Double salary;

}