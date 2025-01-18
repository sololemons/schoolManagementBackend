package com.SCHMA.SchoolManagement.CONTROLLERS;

import com.SCHMA.SchoolManagement.DTOS.StudentDto;
import com.SCHMA.SchoolManagement.ENTITY.Student;
import com.SCHMA.SchoolManagement.SERVICES.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentController {

    @Autowired
    private StudentServices studentServices;

    public StudentController(StudentServices studentServices) {
        this.studentServices = studentServices;
    }

    @GetMapping("/students")
    public ResponseEntity<Page<Student>> getStudents(
            @RequestParam(value = "AdmissionId", required = false) Long AdmissionId,
            @RequestParam(value = "AdmissionYear", required = false) Integer AdmissionYear,
            @RequestParam(value = "Stream", required = false) String Stream,
            Pageable pageable) {

        Page<Student> students = studentServices.getFilteredStudents(AdmissionId, AdmissionYear, Stream, pageable);
        return ResponseEntity.ok(students);
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
