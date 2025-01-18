package com.SCHMA.SchoolManagement.DTOS;

import com.SCHMA.SchoolManagement.ENTITY.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;


}
