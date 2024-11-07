package com.SCHMA.SchoolManagement.SECURITY.AUTHENTICATION.SERVICES;

import com.SCHMA.SchoolManagement.ENTITY.Role;
import com.SCHMA.SchoolManagement.ENTITY.Staff;
import com.SCHMA.SchoolManagement.ENTITY.Student;
import com.SCHMA.SchoolManagement.REPOSITORY.StaffRepository;
import com.SCHMA.SchoolManagement.REPOSITORY.StudentRepository;
import com.SCHMA.SchoolManagement.SECURITY.AUTHENTICATION.ENTITY.AuthenticationRequest;
import com.SCHMA.SchoolManagement.SECURITY.AUTHENTICATION.ENTITY.AuthenticationResponse;
import com.SCHMA.SchoolManagement.SECURITY.AUTHENTICATION.ENTITY.RegisterRequest;
import com.SCHMA.SchoolManagement.SECURITY.CONFIG.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final StudentRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final StaffRepository staffRepository;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Authenticate using email and password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String jwtToken;

        // Check if the email exists in the Student repository
        var studentOptional = repository.findByEmail(request.getEmail());

        if (studentOptional.isPresent()) {
            var student = studentOptional.get();
            // Generate JWT token for student
            jwtToken = jwtService.generateToken(student);
        } else {
            // If not found in Student repository, check in Staff repository
            var staffOptional = staffRepository.findByEmail(request.getEmail());

            if (staffOptional.isPresent()) {
                var staff = staffOptional.get();
                // Generate JWT token for staff
                jwtToken = jwtService.generateToken(staff);
            } else {
                // If the user is not found in either repository, throw an exception
                throw new UsernameNotFoundException("User not found with email: " + request.getEmail());
            }
        }

        // Return the authentication response with the generated JWT token
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    //Register Student
    public AuthenticationResponse registerStudent(RegisterRequest request) {
        Optional<Student> existingStudent = repository.findByEmail(request.getEmail());

        if (existingStudent.isPresent()) {
            Student student = existingStudent.get();

            // Check if firstName and lastName match
            if (!student.getFirstName().equals(request.getFirstName()) || !student.getLastName().equals(request.getLastName())) {
                throw new IllegalArgumentException("Student details do not match the admission records");
            }

            // Proceed with registration if firstName and lastName match
            student.setPassword(passwordEncoder.encode(request.getPassword()));
            repository.save(student);

            var jwtToken = jwtService.generateToken(student);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } else {

            throw new IllegalArgumentException("Email not found for admission");
        }
    }


    // Register Staff
    public AuthenticationResponse registerStaff(RegisterRequest request) {
        Optional<Staff> existingStaff = staffRepository.findByEmail(request.getEmail());

        if (existingStaff.isPresent()) {
            Staff staff = existingStaff.get();

            // Check if firstName and lastName match
            if (!staff.getFirstName().equals(request.getFirstName()) || !staff.getLastName().equals(request.getLastName())) {
                throw new IllegalArgumentException("Staff details do not match the records");
            }

            // Proceed with registration if firstName and lastName match
            staff.setPassword(passwordEncoder.encode(request.getPassword()));
            staffRepository.save(staff);

            var jwtToken = jwtService.generateToken(staff);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } else {
            throw new IllegalArgumentException("Email not found for staff records");
        }
    }



}
