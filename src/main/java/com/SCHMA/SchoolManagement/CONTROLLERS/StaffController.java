package com.SCHMA.SchoolManagement.CONTROLLERS;

import com.SCHMA.SchoolManagement.DTOS.StaffDto;
import com.SCHMA.SchoolManagement.SERVICES.StaffServices;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v3/staff")
public class StaffController {

    @Autowired
    private StaffServices staffServices;


    @GetMapping("/all")
    public ResponseEntity<List<StaffDto>> getAllStaff() {

        return ResponseEntity.ok(staffServices.getAllStaffMembers());
    }

    @GetMapping("/id/{staffID}")
    public ResponseEntity<StaffDto> getStaffById(@PathVariable int staffID) {

        return staffServices.getStaffByStaffID(staffID);
    }

    @PostMapping("/add")

    public ResponseEntity<StaffDto> addStaff(@RequestBody StaffDto staffDTO) {

        return staffServices.addStaffMember(staffDTO);
    }
    @PutMapping("/update/{staffID}")

    public ResponseEntity <StaffDto> updateStaff(@PathVariable int staffID, @RequestBody StaffDto staffDTO) {

        return staffServices.updateStaffByStaffID(staffDTO,staffID);
    }
    @DeleteMapping("delete/{staffID}")
    public ResponseEntity deleteStaffById(int staffID) {
        return staffServices.deleteStaffByID(staffID);
    }



}
