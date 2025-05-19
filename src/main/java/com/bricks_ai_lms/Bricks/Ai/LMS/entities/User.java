package com.bricks_ai_lms.Bricks.Ai.LMS.entities;

import com.bricks_ai_lms.Bricks.Ai.LMS.enums.Gender;
import com.bricks_ai_lms.Bricks.Ai.LMS.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

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

    private UserRole Role;

    private Gender gender;

    private String phone;

    public UserRole getRole() {
        return Role;
    }

    public void setRole(UserRole role) {
        Role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}