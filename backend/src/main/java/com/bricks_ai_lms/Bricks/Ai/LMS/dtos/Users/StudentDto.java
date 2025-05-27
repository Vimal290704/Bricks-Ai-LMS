package com.bricks_ai_lms.Bricks.Ai.LMS.dtos.Users;
import com.bricks_ai_lms.Bricks.Ai.LMS.entities.QuestionBank.Subject;
import com.bricks_ai_lms.Bricks.Ai.LMS.entities.UserEnt.StudentClass;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class StudentDto extends UserDto {

    private String studentId;

    private String rollNumber;

    private String admissionNumber;

    private LocalDate admissionDate;

    private StudentClass batch;

    private String section;

    private String academicYear;

    private String stream;

    private String address;

    private String city;

    private String state;

    private String pinCode;

    private String fatherName;

    private String fatherPhone;

    private String fatherEmail;

    private String fatherOccupation;

    private String motherName;

    private String motherPhone;

    private String motherEmail;

    private String motherOccupation;

    private String guardianName;

    private String guardianPhone;

    private String guardianRelation;

    private String currentStatus;

    private Double annualFees;

    private Double feesPaid;

    private Double feesBalance;

    private LocalDate lastPaymentDate;

    private String feeStructure;

    private List<Subject> subjects;

    private String profileImageUrl;

    private Boolean transportRequired;

    private String pickupPoint;

    private String medicalConditions;

    private String remarks;
}