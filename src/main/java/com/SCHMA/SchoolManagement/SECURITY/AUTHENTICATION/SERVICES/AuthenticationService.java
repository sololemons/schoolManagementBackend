package com.SCHMA.SchoolManagement.SECURITY.AUTHENTICATION.SERVICES;

import com.SCHMA.SchoolManagement.ENTITY.Staff;
import com.SCHMA.SchoolManagement.ENTITY.Student;
import com.SCHMA.SchoolManagement.REPOSITORY.AdminRepository;
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

    private final AdminRepository adminRepository;
    private final StaffRepository staffRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Authenticate user by email and password
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Authenticate using email and password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String jwtToken;
        String role;
        String name;



        // Check in Admin repository
        var adminOptional = adminRepository.findByEmail(request.getEmail());
        if (adminOptional.isPresent()) {
            var admin = adminOptional.get();
            jwtToken = jwtService.generateToken(admin);
            role = admin.getRole().name();  // Using admin's role
            name = admin.getFirstName();
        } else {
            // Check in Staff repository
            var staffOptional = staffRepository.findByEmail(request.getEmail());
            if (staffOptional.isPresent()) {
                var staff = staffOptional.get();
                jwtToken = jwtService.generateToken(staff);
                role = staff.getRole().name();  // Using staff's role
                name= staff.getFirstName();
            } else {
                // Check in Student repository
                var studentOptional = studentRepository.findByEmail(request.getEmail());
                if (studentOptional.isPresent()) {
                    var student = studentOptional.get();
                    jwtToken = jwtService.generateToken(student);
                    role = student.getRole().name();  // Using student's role
                    name= student.getFirstName();

                } else {
                    throw new UsernameNotFoundException("User not found with email: " + request.getEmail());
                }
            }
        }

        // Return the authentication response with the generated JWT token
        return AuthenticationResponse.builder()
                .firstName(name)
                .token(jwtToken)
                .role(role)


                .build();
    }

    // Fetch the role by email from all repositories
    public String fetchUserRoleByEmail(String email) {
        // Check in Admin Repository
        var admin = adminRepository.findByEmail(email);
        if (admin.isPresent()) {
            return admin.get().getRole().name();  // Correct role fetching
        }

        var staff = staffRepository.findByEmail(email);
        if (staff.isPresent()) {
            return staff.get().getRole().name();  // ROLE_STAFF
        }

        // Check in Student Repository
        var student = studentRepository.findByEmail(email);
        if (student.isPresent()) {
            return student.get().getRole().name();  // ROLE_STUDENT
        }

        // If not found in any repository
        throw new IllegalArgumentException("Email not found in records for any user type");
    }

    // Register User based on their role
    public AuthenticationResponse registerUser(RegisterRequest request) {
        String role = fetchUserRoleByEmail(request.getEmail());

        switch (role.toUpperCase()) {
            case "ROLE_STUDENT":
                return registerStudent(request);
            case "ROLE_STAFF":
                return registerStaff(request);
            case "ROLE_ADMIN":
                throw new UnsupportedOperationException("Admin registration is not supported via this endpoint");
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }
    }

    // Register Student
    public AuthenticationResponse registerStudent(RegisterRequest request) {
        Optional<Student> existingStudent = studentRepository.findByEmail(request.getEmail().toLowerCase());
        if (existingStudent.isPresent()) {
            Student student = existingStudent.get();
            // Check if firstName and lastName match
            if (!student.getFirstName().equals(request.getFirstName()) || !student.getLastName().equals(request.getLastName())) {
                throw new IllegalArgumentException("Student details do not match the admission records");
            }
            // Proceed with registration if firstName and lastName match
            student.setPassword(passwordEncoder.encode(request.getPassword()));
            studentRepository.save(student);

            String jwtToken = jwtService.generateToken(student);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } else {
            throw new IllegalArgumentException("Email not found for student admission");
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

            String jwtToken = jwtService.generateToken(staff);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } else {
            throw new IllegalArgumentException("Email not found for staff records");
        }
    }
}
