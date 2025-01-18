package com.SCHMA.SchoolManagement.SECURITY.AUTHENTICATION.CONTROLLER;

import com.SCHMA.SchoolManagement.REPOSITORY.AdminRepository;
import com.SCHMA.SchoolManagement.REPOSITORY.StaffRepository;
import com.SCHMA.SchoolManagement.REPOSITORY.StudentRepository;
import com.SCHMA.SchoolManagement.SECURITY.AUTHENTICATION.ENTITY.AuthenticationRequest;
import com.SCHMA.SchoolManagement.SECURITY.AUTHENTICATION.ENTITY.AuthenticationResponse;
import com.SCHMA.SchoolManagement.SECURITY.AUTHENTICATION.ENTITY.RegisterRequest;
import com.SCHMA.SchoolManagement.SECURITY.AUTHENTICATION.SERVICES.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthenticationController {

    @Autowired
    private final AuthenticationService service;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.registerUser(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
