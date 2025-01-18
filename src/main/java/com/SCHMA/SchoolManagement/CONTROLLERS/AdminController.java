package com.SCHMA.SchoolManagement.CONTROLLERS;

import com.SCHMA.SchoolManagement.DTOS.AdminDto;
import com.SCHMA.SchoolManagement.DTOS.StudentDto;
import com.SCHMA.SchoolManagement.SERVICES.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Admin")
        @CrossOrigin(origins = "http://localhost:5173")
public class AdminController {
    @Autowired
    private AdminServices adminServices;

@PostMapping("/add")
    public ResponseEntity<AdminDto> addAdmin(@RequestBody AdminDto Admin) {

        return adminServices.addAdmin(Admin);
    }
}
