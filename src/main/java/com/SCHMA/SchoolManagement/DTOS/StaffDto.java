package com.SCHMA.SchoolManagement.DTOS;

import com.SCHMA.SchoolManagement.ENTITY.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffDto {
    private String firstName;
    private String lastName;
    private String email;
    private String department;
    private String position;
    private String Gender;
    private String phoneNumber;
    private Long birthYear;
    private Long admissionYear;
    private Role role;


}
