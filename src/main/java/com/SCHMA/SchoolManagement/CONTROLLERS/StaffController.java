package com.SCHMA.SchoolManagement.CONTROLLERS;

import com.SCHMA.SchoolManagement.DTOS.StaffDto;
import com.SCHMA.SchoolManagement.ENTITY.Staff;
import com.SCHMA.SchoolManagement.ENTITY.Student;
import com.SCHMA.SchoolManagement.SERVICES.StaffServices;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
@CrossOrigin(origins = "http://localhost:5173")
public class StaffController {

    @Autowired
    private StaffServices staffServices;


    @GetMapping("/all")
    public ResponseEntity<List<StaffDto>> getAllStaff() {

        return ResponseEntity.ok(staffServices.getAllStaffMembers());
    }
    @GetMapping("/staff")
    public ResponseEntity<Page<Staff>> getStaff(
            @RequestParam(value = "staffID", required = false) Long staffID,
            @RequestParam(value = "admissionYear", required = false) Long admissionYear,
            @RequestParam(value = "department", required = false) String department,
            Pageable pageable) {

        Page<Staff> staff = staffServices.getFilteredStaff(staffID, admissionYear, department, pageable);
        return ResponseEntity.ok(staff);
    }


    @GetMapping("/id/{staffID}")
    public ResponseEntity<StaffDto> getStaffById(@PathVariable Long staffID) {

        return staffServices.getStaffByStaffID(staffID);
    }

    @PostMapping("/add")

    public ResponseEntity<StaffDto> addStaff(@RequestBody StaffDto staffDTO) {

        return staffServices.addStaffMember(staffDTO);
    }
    @PutMapping("/update/{staffID}")

    public ResponseEntity <StaffDto> updateStaff(@PathVariable Long staffID, @RequestBody StaffDto staffDTO) {

        return staffServices.updateStaffByStaffID(staffDTO,staffID);
    }
    @DeleteMapping("delete/{staffID}")
    public ResponseEntity deleteStaffById(Long staffID) {

        return staffServices.deleteStaffByID(staffID);
    }



}
