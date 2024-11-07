package com.SCHMA.SchoolManagement.CONTROLLERS;

import com.SCHMA.SchoolManagement.DTOS.StudentDto;
import com.SCHMA.SchoolManagement.ENTITY.Student;
import com.SCHMA.SchoolManagement.SERVICES.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    @Autowired
    private StudentServices studentServices;

    public StudentController(StudentServices studentServices) {
        this.studentServices = studentServices;
    }


    @GetMapping("/all")
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return ResponseEntity.ok(studentServices.getAllStudents()); // Return as ResponseEntity
    }

    @GetMapping("/get/{Admissionid}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long Admissionid) {
        return studentServices.getStudentByAdmissionId(Admissionid);
    }

    @PostMapping("/add")
    public ResponseEntity<StudentDto> addStudent(@RequestBody StudentDto student) {

        return studentServices.addStudent(student);
    }
    @PutMapping("/update/{AdmissionId}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable Long AdmissionId, @RequestBody StudentDto student) {
        return studentServices.updateStudent(AdmissionId,student);
    }

    @DeleteMapping("/delete/{Admissionid}")
    public ResponseEntity<StudentDto> deleteStudent(@PathVariable Long Admissionid) {
        return studentServices.deleteStudent(Admissionid);
    }






}
