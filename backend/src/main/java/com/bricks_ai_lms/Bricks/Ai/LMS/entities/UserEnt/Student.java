package com.bricks_ai_lms.bricks.ai.lms.entities.UserEnt;

import com.bricks_ai_lms.bricks.ai.lms.entities.QuestionBank.Subject;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "students")
@DiscriminatorValue("STUDENT")
@PrimaryKeyJoinColumn(name = "user_id")
public class Student extends User {

    @Column(name = "student_id", unique = true)
    private String studentId;

    @Column(name = "roll_number")
    private String rollNumber;

    @Column(name = "admission_number")
    private String admissionNumber;

    @Column(name = "admission_date")
    private LocalDate admissionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_class_id", nullable = false)
    private StudentClass batch;

    @Column(name = "academic_year")
    private String academicYear;

    @Column(name = "stream")
    private String stream;

    private String address;

    private String city;

    private String state;

    private String pinCode;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "father_phone")
    private String fatherPhone;

    @Column(name = "father_email")
    private String fatherEmail;

    @Column(name = "father_occupation")
    private String fatherOccupation;

    @Column(name = "mother_name")
    private String motherName;

    @Column(name = "mother_phone")
    private String motherPhone;

    @Column(name = "mother_email")
    private String motherEmail;

    @Column(name = "mother_occupation")
    private String motherOccupation;

    @Column(name = "guardian_name")
    private String guardianName;

    @Column(name = "guardian_phone")
    private String guardianPhone;

    @Column(name = "guardian_relation")
    private String guardianRelation;

    @Column(name = "current_status")
    private String currentStatus;

    @Column(name = "annual_fees")
    private Double annualFees;

    @Column(name = "fees_paid")
    private Double feesPaid;

    @Column(name = "fees_balance")
    private Double feesBalance;

    @Column(name = "last_payment_date")
    private LocalDate lastPaymentDate;

    @Column(name = "fee_structure")
    private String feeStructure;

    @ElementCollection
    @CollectionTable(name = "student_subjects", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "subject")
    private List<Subject> subjects;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "transport_required")
    private Boolean transportRequired = false;

    @Column(name = "pickup_point")
    private String pickupPoint;

    @Column(name = "medical_conditions")
    private String medicalConditions;

    @Column(columnDefinition = "TEXT")
    private String remarks;
}