package com.bricks_ai_lms.Bricks.Ai.LMS.dtos.Users;
import com.bricks_ai_lms.Bricks.Ai.LMS.entities.QuestionBank.Subject;
import com.bricks_ai_lms.Bricks.Ai.LMS.entities.UserEnt.StudentClass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TeacherDto extends UserDto{

    private String teacherId;

    private String employeeId;

    private String qualification;

    private Integer teachingExperience;

    private LocalDate joiningDate;

    private String address;

    private String city;

    private String state;

    private String pinCode;

    private String alternatePhone;

    private String emergencyContactName;

    private String emergencyContactPhone;

    private String emergencyContactRelation;

    private Double salary;

    private String bankAccountNumber;

    private String bankName;

    private String ifscCode;

    private List<Subject> subjectsTeaching;

    private List<StudentClass> classesTeaching;

    private String specialization;

    private String bio;

    private String profileImageUrl;

    private Boolean isClassTeacher;

    private String classTeacherOf;
}
