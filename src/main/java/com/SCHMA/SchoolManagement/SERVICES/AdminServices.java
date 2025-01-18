package com.SCHMA.SchoolManagement.SERVICES;

import com.SCHMA.SchoolManagement.DTOS.AdminDto;
import com.SCHMA.SchoolManagement.ENTITY.Admin;
import com.SCHMA.SchoolManagement.REPOSITORY.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServices {
    @Autowired
    private final AdminRepository adminRepository;

private final PasswordEncoder passwordEncoder;

    public AdminServices(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<AdminDto> addAdmin(AdminDto admin) {

        Admin admin1 = new Admin();

        admin1.setPassword(passwordEncoder.encode(admin.getPassword()));

        admin1.setFirstName(admin.getFirstName());
        admin1.setLastName(admin.getLastName());
        admin1.setEmail(admin.getEmail());
        admin1.setRole(admin.getRole());
        adminRepository.save(admin1);
        return ResponseEntity.ok(admin);

    }
}
