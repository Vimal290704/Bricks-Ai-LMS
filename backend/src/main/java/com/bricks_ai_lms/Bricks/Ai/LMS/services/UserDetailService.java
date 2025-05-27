package com.bricks_ai_lms.bricks.ai.lms.services;

import com.bricks_ai_lms.bricks.ai.lms.dtos.Users.*;
import com.bricks_ai_lms.bricks.ai.lms.entities.UserEnt.Admin;
import com.bricks_ai_lms.bricks.ai.lms.entities.UserEnt.Student;
import com.bricks_ai_lms.bricks.ai.lms.entities.UserEnt.Teacher;
import com.bricks_ai_lms.bricks.ai.lms.entities.UserEnt.User;
import com.bricks_ai_lms.bricks.ai.lms.enums.UserRole;
import com.bricks_ai_lms.bricks.ai.lms.repositories.Users.AdminRepository;
import com.bricks_ai_lms.bricks.ai.lms.repositories.Users.StudentRepository;
import com.bricks_ai_lms.bricks.ai.lms.repositories.Users.TeacherRepository;
import com.bricks_ai_lms.bricks.ai.lms.repositories.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserDetailService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetailResponseDto getUserDetailsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return createUserDetailResponse(user);
    }

    public UserDetailResponseDto getUserDetailsById(Long userId, String requestingUsername) {
        User requestingUser = userRepository.findByUsername(requestingUsername)
                .orElseThrow(() -> new RuntimeException("Requesting user not found"));

        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!requestingUser.getRole().equals(UserRole.ADMIN) &&
                !requestingUser.getId().equals(targetUser.getId())) {
            throw new SecurityException("Access denied");
        }

        return createUserDetailResponse(targetUser);
    }

    private UserDetailResponseDto createUserDetailResponse(User user) {
        return switch (user.getRole()) {
            case TEACHER -> {
                Teacher teacher = teacherRepository.findById(user.getId())
                        .orElseThrow(() -> new RuntimeException("Teacher details not found"));
                yield new UserDetailResponseDto(UserRole.TEACHER, convertToTeacherDto(teacher));
            }
            case STUDENT -> {
                Student student = studentRepository.findById(user.getId())
                        .orElseThrow(() -> new RuntimeException("Student details not found"));
                yield new UserDetailResponseDto(UserRole.STUDENT, convertToStudentDto(student));
            }
            case ADMIN -> {
                Admin admin = adminRepository.findById(user.getId())
                        .orElseThrow(() -> new RuntimeException("Admin details not found"));
                yield new UserDetailResponseDto(UserRole.ADMIN, convertToAdminDto(admin));
            }
            default -> new UserDetailResponseDto(user.getRole(), convertToUserDto(user));
        };
    }

    public UserDetailResponseDto updateUserProfile(String username, UserUpdateRequestDto updateRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        updateCommonFields(user, updateRequest);

        switch (user.getRole()) {
            case TEACHER:
                Teacher teacher = (Teacher) teacherRepository.findById(user.getId())
                        .orElseThrow(() -> new RuntimeException("Teacher not found"));
                updateTeacherSpecificFields(teacher, updateRequest);
                teacherRepository.save(teacher);
                break;

            case STUDENT:
                Student student = studentRepository.findById(user.getId())
                        .orElseThrow(() -> new RuntimeException("Student not found"));
                updateStudentSpecificFields(student, updateRequest);
                studentRepository.save(student);
                break;

            case ADMIN:
                Admin admin = adminRepository.findById(user.getId())
                        .orElseThrow(() -> new RuntimeException("Admin not found"));
                updateAdminSpecificFields(admin, updateRequest);
                adminRepository.save(admin);
                break;
        }

        return createUserDetailResponse(user);
    }

    public UserDto getBasicUserInfo(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return convertToUserDto(user);
    }

    private void updateCommonFields(User user, UserUpdateRequestDto updateRequest) {
        if (updateRequest.getName() != null) user.setName(updateRequest.getName());
        if (updateRequest.getEmail() != null) user.setEmail(updateRequest.getEmail());
        if (updateRequest.getGender() != null) user.setGender(updateRequest.getGender());
        if (updateRequest.getPhone() != null) user.setPhone(updateRequest.getPhone());
        if (updateRequest.getDateOfBirth() != null) user.setDateOfBirth(updateRequest.getDateOfBirth());
        if (updateRequest.getHeight() != null) user.setHeight(updateRequest.getHeight());
        if (updateRequest.getWeight() != null) user.setWeight(updateRequest.getWeight());
        if (updateRequest.getBloodGroup() != null) user.setBloodGroup(updateRequest.getBloodGroup());
    }

    private void updateTeacherSpecificFields(Teacher teacher, UserUpdateRequestDto updateRequest) {
        if (updateRequest.getAddress() != null) teacher.setAddress(updateRequest.getAddress());
        if (updateRequest.getCity() != null) teacher.setCity(updateRequest.getCity());
        if (updateRequest.getState() != null) teacher.setState(updateRequest.getState());
        if (updateRequest.getPinCode() != null) teacher.setPinCode(updateRequest.getPinCode());
        if (updateRequest.getProfileImageUrl() != null) teacher.setProfileImageUrl(updateRequest.getProfileImageUrl());
    }

    private void updateStudentSpecificFields(Student student, UserUpdateRequestDto updateRequest) {
        if (updateRequest.getAddress() != null) student.setAddress(updateRequest.getAddress());
        if (updateRequest.getCity() != null) student.setCity(updateRequest.getCity());
        if (updateRequest.getState() != null) student.setState(updateRequest.getState());
        if (updateRequest.getPinCode() != null) student.setPinCode(updateRequest.getPinCode());
        if (updateRequest.getProfileImageUrl() != null) student.setProfileImageUrl(updateRequest.getProfileImageUrl());
    }

    private void updateAdminSpecificFields(Admin admin, UserUpdateRequestDto updateRequest) {
        if (updateRequest.getAddress() != null) admin.setAddress(updateRequest.getAddress());
        if (updateRequest.getCity() != null) admin.setCity(updateRequest.getCity());
        if (updateRequest.getState() != null) admin.setState(updateRequest.getState());
        if (updateRequest.getPinCode() != null) admin.setPinCode(updateRequest.getPinCode());
        if (updateRequest.getProfileImageUrl() != null) admin.setProfileImageUrl(updateRequest.getProfileImageUrl());
    }

    private UserDto convertToUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setGender(user.getGender());
        dto.setPhone(user.getPhone());
        dto.setSchool(user.getSchool());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setHeight(user.getHeight());
        dto.setWeight(user.getWeight());
        dto.setBloodGroup(user.getBloodGroup());
        dto.setIsActive(user.getIsActive());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }

    private TeacherDto convertToTeacherDto(Teacher teacher) {
        TeacherDto dto = new TeacherDto();
        dto.setId(teacher.getId());
        dto.setUsername(teacher.getUsername());
        dto.setName(teacher.getName());
        dto.setEmail(teacher.getEmail());
        dto.setRole(teacher.getRole());
        dto.setGender(teacher.getGender());
        dto.setPhone(teacher.getPhone());
        dto.setSchool(teacher.getSchool());
        dto.setDateOfBirth(teacher.getDateOfBirth());
        dto.setHeight(teacher.getHeight());
        dto.setWeight(teacher.getWeight());
        dto.setBloodGroup(teacher.getBloodGroup());
        dto.setIsActive(teacher.getIsActive());
        dto.setCreatedAt(teacher.getCreatedAt());
        dto.setUpdatedAt(teacher.getUpdatedAt());

        dto.setTeacherId(teacher.getTeacherId());
        dto.setEmployeeId(teacher.getEmployeeId());
        dto.setQualification(teacher.getQualification());
        dto.setTeachingExperience(teacher.getTeachingExperience());
        dto.setJoiningDate(teacher.getJoiningDate());
        dto.setSpecialization(teacher.getSpecialization());
        dto.setAddress(teacher.getAddress());
        dto.setCity(teacher.getCity());
        dto.setState(teacher.getState());
        dto.setPinCode(teacher.getPinCode());
        dto.setAlternatePhone(teacher.getAlternatePhone());
        dto.setEmergencyContactName(teacher.getEmergencyContactName());
        dto.setEmergencyContactPhone(teacher.getEmergencyContactPhone());
        dto.setEmergencyContactRelation(teacher.getEmergencyContactRelation());
        dto.setSalary(teacher.getSalary());
        dto.setBankAccountNumber(teacher.getBankAccountNumber());
        dto.setBankName(teacher.getBankName());
        dto.setIfscCode(teacher.getIfscCode());
        dto.setSubjectsTeaching(teacher.getSubjectsTeaching());
        dto.setClassesTeaching(teacher.getClassesTeaching());
        dto.setBio(teacher.getBio());
        dto.setProfileImageUrl(teacher.getProfileImageUrl());
        dto.setIsClassTeacher(teacher.getIsClassTeacher());
        dto.setClassTeacherOf(teacher.getClassTeacherOf());

        return dto;
    }

    private StudentDto convertToStudentDto(Student student) {
        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setUsername(student.getUsername());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setRole(student.getRole());
        dto.setGender(student.getGender());
        dto.setPhone(student.getPhone());
        dto.setSchool(student.getSchool());
        dto.setDateOfBirth(student.getDateOfBirth());
        dto.setHeight(student.getHeight());
        dto.setWeight(student.getWeight());
        dto.setBloodGroup(student.getBloodGroup());
        dto.setIsActive(student.getIsActive());
        dto.setCreatedAt(student.getCreatedAt());
        dto.setUpdatedAt(student.getUpdatedAt());
        dto.setStudentId(student.getStudentId());
        dto.setRollNumber(student.getRollNumber());
        dto.setAdmissionNumber(student.getAdmissionNumber());
        dto.setAdmissionDate(student.getAdmissionDate());
        dto.setAcademicYear(student.getAcademicYear());
        dto.setStream(student.getStream());
        dto.setAddress(student.getAddress());
        dto.setCity(student.getCity());
        dto.setState(student.getState());
        dto.setPinCode(student.getPinCode());
        dto.setFatherName(student.getFatherName());
        dto.setFatherPhone(student.getFatherPhone());
        dto.setFatherEmail(student.getFatherEmail());
        dto.setFatherOccupation(student.getFatherOccupation());
        dto.setMotherName(student.getMotherName());
        dto.setMotherPhone(student.getMotherPhone());
        dto.setMotherEmail(student.getMotherEmail());
        dto.setMotherOccupation(student.getMotherOccupation());
        dto.setGuardianName(student.getGuardianName());
        dto.setGuardianPhone(student.getGuardianPhone());
        dto.setGuardianRelation(student.getGuardianRelation());
        dto.setCurrentStatus(student.getCurrentStatus());
        dto.setAnnualFees(student.getAnnualFees());
        dto.setFeesPaid(student.getFeesPaid());
        dto.setFeesBalance(student.getFeesBalance());
        dto.setLastPaymentDate(student.getLastPaymentDate());
        dto.setFeeStructure(student.getFeeStructure());
        dto.setSubjects(student.getSubjects());
        dto.setProfileImageUrl(student.getProfileImageUrl());
        dto.setTransportRequired(student.getTransportRequired());
        dto.setPickupPoint(student.getPickupPoint());
        dto.setMedicalConditions(student.getMedicalConditions());
        dto.setRemarks(student.getRemarks());
        return dto;
    }

    private AdminDto convertToAdminDto(Admin admin) {
        AdminDto dto = new AdminDto();
        dto.setId(admin.getId());
        dto.setUsername(admin.getUsername());
        dto.setName(admin.getName());
        dto.setEmail(admin.getEmail());
        dto.setRole(admin.getRole());
        dto.setGender(admin.getGender());
        dto.setPhone(admin.getPhone());
        dto.setSchool(admin.getSchool());
        dto.setDateOfBirth(admin.getDateOfBirth());
        dto.setHeight(admin.getHeight());
        dto.setWeight(admin.getWeight());
        dto.setBloodGroup(admin.getBloodGroup());
        dto.setIsActive(admin.getIsActive());
        dto.setCreatedAt(admin.getCreatedAt());
        dto.setUpdatedAt(admin.getUpdatedAt());
        dto.setAdminId(admin.getAdminId());
        dto.setEmployeeId(admin.getEmployeeId());
        dto.setDesignation(admin.getDesignation());
        dto.setDepartment(admin.getDepartment());
        dto.setJoiningDate(admin.getJoiningDate());
        dto.setAddress(admin.getAddress());
        dto.setCity(admin.getCity());
        dto.setState(admin.getState());
        dto.setPinCode(admin.getPinCode());
        dto.setPermissions(admin.getPermissions());
        dto.setProfileImageUrl(admin.getProfileImageUrl());
        dto.setLastLoginDate(admin.getLastLoginDate());
        dto.setSalary(admin.getSalary());
        return dto;
    }

}
