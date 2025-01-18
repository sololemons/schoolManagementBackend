package com.SCHMA.SchoolManagement.SERVICES;

import com.SCHMA.SchoolManagement.DTOS.StaffDto;
import com.SCHMA.SchoolManagement.DTOS.StudentDto;
import com.SCHMA.SchoolManagement.ENTITY.Staff;
import com.SCHMA.SchoolManagement.ENTITY.Student;
import com.SCHMA.SchoolManagement.REPOSITORY.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StaffServices {
    @Autowired
    private final StaffRepository staffRepository;

    public StaffServices(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }


    public List<StaffDto> getAllStaffMembers() {

        List<Staff> staffList = staffRepository.findAll();
        return staffList.stream()
                .map(this::mapToDto) // Convert each Student entity to a StudentDto
                .collect(Collectors.toList()); // Collect the results as a List

    }
//Utility Class to convert Staff to StaffDto
    private StaffDto mapToDto(Staff staff) {
        return new StaffDto(
                staff.getFirstName(),
                staff.getLastName(),
                staff.getEmail(),
                staff.getDepartment(),
                staff.getPosition(),
                staff.getGender(),
                staff.getPhoneNumber(),
                staff.getBirthYear(),
                staff.getAdmissionYear(),
                staff.getRole()
        );
    }


    public ResponseEntity<StaffDto> getStaffByStaffID(Long staffID) {

        Optional<Staff> staffOptional = staffRepository.findById(staffID);

        if (staffOptional.isPresent()) {
            Staff staff = staffOptional.get();
            StaffDto staffDto = mapToDto(staff);
            return ResponseEntity.ok(staffDto);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }


    public ResponseEntity<StaffDto> addStaffMember(StaffDto staffDto) {
        Staff newStaff = new Staff();
        newStaff.setFirstName(staffDto.getFirstName());
        newStaff.setLastName(staffDto.getLastName());
        newStaff.setEmail(staffDto.getEmail());
        newStaff.setDepartment(staffDto.getDepartment());
        newStaff.setPosition(staffDto.getPosition());
        newStaff.setGender(staffDto.getGender());
        newStaff.setPhoneNumber(staffDto.getPhoneNumber());
        newStaff.setBirthYear(staffDto.getBirthYear());
        newStaff.setAdmissionYear(staffDto.getAdmissionYear());
        newStaff.setRole(staffDto.getRole());
        Staff savedStaff = staffRepository.save(newStaff);
        StaffDto savedStaffDto = mapToDto(savedStaff);
        return ResponseEntity.ok(savedStaffDto);


    }


    public ResponseEntity<StaffDto> updateStaffByStaffID(StaffDto staffDTO, Long staffID) {

        Optional<Staff> staffOptional = staffRepository.findById(staffID);
        if (staffOptional.isPresent()) {
            Staff staffToUpdate = staffOptional.get();
            staffToUpdate.setFirstName(staffDTO.getFirstName());
            staffToUpdate.setLastName(staffDTO.getLastName());
            staffToUpdate.setEmail(staffDTO.getEmail());
            staffToUpdate.setDepartment(staffDTO.getDepartment());
            staffToUpdate.setPosition(staffDTO.getPosition());
            staffToUpdate.setGender(staffDTO.getGender());
            staffToUpdate.setPhoneNumber(staffDTO.getPhoneNumber());
            staffToUpdate.setBirthYear(staffDTO.getBirthYear());
            staffToUpdate.setAdmissionYear(staffDTO.getAdmissionYear());
            staffToUpdate.setRole(staffDTO.getRole());

            Staff savedStaff = staffRepository.save(staffToUpdate);
            StaffDto savedStaffDto = mapToDto(savedStaff);
            return ResponseEntity.ok(savedStaffDto);
        }
           else{
               return ResponseEntity.notFound().build();
            }

    }

    public ResponseEntity deleteStaffByID(Long staffID) {
        Optional<Staff> staffOptional = staffRepository.findById(staffID);
        if (staffOptional.isPresent()) {
            Staff staff = staffOptional.get();
            staffRepository.delete(staff);
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    public Page<Staff> getFilteredStaff(Long staffID, Long admissionYear, String department, Pageable pageable) {

        return staffRepository.findByFilters(staffID,admissionYear,department,pageable);
    }
}
