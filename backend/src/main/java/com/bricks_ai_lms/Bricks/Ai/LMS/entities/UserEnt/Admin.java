package com.bricks_ai_lms.bricks.ai.lms.entities.UserEnt;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.*;

@Setter
@Getter
@Entity
@Table(name = "admins")
@DiscriminatorValue("ADMIN")
@PrimaryKeyJoinColumn(name = "user_id")
public class Admin extends User {

    @Column(name = "admin_id", unique = true)
    private String adminId;

    @Column(name = "employee_id", unique = true)
    private String employeeId;

    private String designation;

    @Column(name = "department")
    private String department;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    private String address;

    private String city;

    private String state;

    private String pinCode;

    @ElementCollection
    @CollectionTable(name = "admin_permissions", joinColumns = @JoinColumn(name = "admin_id"))
    @Column(name = "permission")
    private List<String> permissions;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "last_login_date")
    private LocalDate lastLoginDate;

    private Double salary;

}