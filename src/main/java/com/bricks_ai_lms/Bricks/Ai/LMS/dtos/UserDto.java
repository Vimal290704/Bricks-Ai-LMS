package com.bricks_ai_lms.Bricks.Ai.LMS.dtos;

import com.bricks_ai_lms.Bricks.Ai.LMS.enums.Gender;
import com.bricks_ai_lms.Bricks.Ai.LMS.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String name;

    private String email;

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String password;

    private UserRole Role;

    private Gender gender;

    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return Role;
    }

    public void setRole(UserRole role) {
        Role = role;
    }

}