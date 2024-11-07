package com.SCHMA.SchoolManagement.DTOS;
import com.SCHMA.SchoolManagement.ENTITY.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class StudentDto {
    private String firstName;
    private String lastName;
    private Long AdmissionYear;
    private String Gender;
    private String Stream;
    private String Position;
    private String email;
    private Role role;


}
