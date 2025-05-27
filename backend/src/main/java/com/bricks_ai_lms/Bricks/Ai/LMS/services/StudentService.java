package com.bricks_ai_lms.Bricks.Ai.LMS.services;

import com.bricks_ai_lms.Bricks.Ai.LMS.entities.QuestionBank.Subject;
import com.bricks_ai_lms.Bricks.Ai.LMS.entities.UserEnt.Student;
import com.bricks_ai_lms.Bricks.Ai.LMS.entities.UserEnt.StudentClass;
import com.bricks_ai_lms.Bricks.Ai.LMS.enums.Gender;
import com.bricks_ai_lms.Bricks.Ai.LMS.enums.UserRole;
import com.bricks_ai_lms.Bricks.Ai.LMS.repositories.QuestionBank.SubjectRepository;
import com.bricks_ai_lms.Bricks.Ai.LMS.repositories.Users.StudentRepository;
import com.bricks_ai_lms.Bricks.Ai.LMS.repositories.Users.StudentClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentClassRepository studentClassRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    public void createStudent() {
        Student student = new Student();
        student.setName("Vimal Kumar Yadav");
        student.setUsername("vimalyad2907");
        student.setEmail("vimalyadavkr001@gmail.com");
        student.setPassword("defaultPassword123");
        student.setRole(UserRole.STUDENT);
        student.setGender(Gender.MALE);
        student.setPhone("9876543210");
        student.setSchool("Bricks International School");
        student.setDateOfBirth(LocalDate.of(2007, 7, 29));
        student.setHeight(175);
        student.setWeight(65);
        student.setBloodGroup("B+");
        student.setIsActive(true);
        student.setStudentId("24BCS10273");
        student.setRollNumber("13-B");
        student.setAdmissionNumber("5265");
        student.setAdmissionDate(LocalDate.of(2025, 4, 1));
        StudentClass studentClass = studentClassRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("StudentClass not found"));
        student.setBatch(studentClass);
        student.setAcademicYear("2025-2026");
        student.setStream("Science");
        student.setAddress("123, Green Street");
        student.setCity("Lucknow");
        student.setState("Uttar Pradesh");
        student.setPinCode("226001");
        student.setFatherName("Mr. Bhulloo Lal Yadav");
        student.setFatherPhone("9876543211");
        student.setFatherEmail("bhulloo.yadav@example.com");
        student.setFatherOccupation("Farmer");
        student.setMotherName("Mrs. Sushila Devi");
        student.setMotherPhone("9876543212");
        student.setMotherEmail("sushila.devi@example.com");
        student.setMotherOccupation("Homemaker");
        student.setGuardianName("Mr. Ramesh Kumar");
        student.setGuardianPhone("9876543213");
        student.setGuardianRelation("Uncle");
        student.setCurrentStatus("Active");
        student.setAnnualFees(50000.0);
        student.setFeesPaid(20000.0);
        student.setFeesBalance(30000.0);
        student.setLastPaymentDate(LocalDate.of(2025, 5, 15));
        student.setFeeStructure("Monthly");
        List<Subject> subjects = subjectRepository.findAllById(Arrays.asList(1, 2, 3));
        student.setSubjects(subjects);
        student.setProfileImageUrl("https://example.com/images/vimal.jpg");
        student.setTransportRequired(true);
        student.setPickupPoint("Sector 5 Bus Stop");

        student.setMedicalConditions("None");
        student.setRemarks("Excellent performance in last year exams.");

        // Save student
        studentRepository.save(student);
    }

}
