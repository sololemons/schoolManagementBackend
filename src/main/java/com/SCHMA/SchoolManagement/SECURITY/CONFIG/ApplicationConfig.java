package com.SCHMA.SchoolManagement.SECURITY.CONFIG;

import com.SCHMA.SchoolManagement.REPOSITORY.AdminRepository;
import com.SCHMA.SchoolManagement.REPOSITORY.StaffRepository;
import com.SCHMA.SchoolManagement.REPOSITORY.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final AdminRepository adminRepository;
    private final StaffRepository staffRepository;
    private final StudentRepository studentRepository;

    // Generalized UserDetailsService to support Admin, Staff, and Student
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            // Try to find the user in Admin repository
            var admin = adminRepository.findByEmail(username);
            if (admin.isPresent()) {
                return admin.get();
            }

            // Try to find the user in Staff repository
            var staff = staffRepository.findByEmail(username);
            if (staff.isPresent()) {
                return staff.get();
            }

            // Try to find the user in Student repository
            var student = studentRepository.findByEmail(username);
            if (student.isPresent()) {
                return student.get();
            }

            throw new UsernameNotFoundException("User not found with email: " + username);
        };
    }

    // AuthenticationProvider for validating user credentials
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    // AuthenticationManager Bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Password Encoder Bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
