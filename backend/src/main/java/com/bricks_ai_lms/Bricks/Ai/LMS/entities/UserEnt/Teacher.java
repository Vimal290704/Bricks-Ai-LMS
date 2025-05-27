package com.bricks_ai_lms.Bricks.Ai.LMS.entities.UserEnt;

import com.bricks_ai_lms.Bricks.Ai.LMS.entities.QuestionBank.Subject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "teachers")
@DiscriminatorValue("TEACHER")
@PrimaryKeyJoinColumn(name = "user_id")
public class Teacher extends User {

    @Column(name = "teacher_id", unique = true)
    private String teacherId;

    @Column(name = "employee_id", unique = true)
    private String employeeId;

    private String qualification;

    @Column(name = "teaching_experience")
    private Integer teachingExperience;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    private String address;

    private String city;

    private String state;

    private String pinCode;

    @Column(name = "alternate_phone")
    private String alternatePhone;

    @Column(name = "emergency_contact_name")
    private String emergencyContactName;

    @Column(name = "emergency_contact_phone")
    private String emergencyContactPhone;

    @Column(name = "emergency_contact_relation")
    private String emergencyContactRelation;

    private Double salary;

    @Column(name = "bank_account_number")
    private String bankAccountNumber;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @ElementCollection
    @CollectionTable(name = "teacher_subjects", joinColumns = @JoinColumn(name = "teacher_id"))
    @Column(name = "subject")
    private List<Subject> subjectsTeaching;

    @ManyToMany
    @JoinTable(
            name = "teacher_classes",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "student_class_id")
    )
    private List<StudentClass> classesTeaching;


    @Column(name = "specialization")
    private String specialization;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(name = "is_class_teacher")
    private Boolean isClassTeacher = false;

    @Column(name = "class_teacher_of")
    private String classTeacherOf;

    @Column(name = "profile_image_url")
    private String profileImageUrl;
}
