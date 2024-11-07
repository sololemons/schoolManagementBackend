package com.SCHMA.SchoolManagement.SERVICES;

import com.SCHMA.SchoolManagement.DTOS.StudentDto;
import com.SCHMA.SchoolManagement.ENTITY.Student;
import com.SCHMA.SchoolManagement.REPOSITORY.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServices {

    @Autowired
    private StudentRepository studentRepository;

    public StudentServices(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public List<StudentDto> getAllStudents() {
        // Retrieve all Student entities from the database
        List<Student> students = studentRepository.findAll();

        // Convert the list of Student entities to a list of StudentDto objects
        return students.stream()
                .map(this::mapToDto) // Convert each Student entity to a StudentDto
                .collect(Collectors.toList()); // Collect the results as a List


    }
    // UTILITY CLASS to convert student to studentDTO
    private StudentDto mapToDto(Student student) {
        return new StudentDto(
                student.getFirstName(),
                student.getLastName(),
                student.getAdmissionYear(),
                student.getGender(),
                student.getStream(),
                student.getPosition(),
                student.getEmail(),
                student.getRole()

        );
    }

    public ResponseEntity<StudentDto> getStudentByAdmissionId(Long admissionId) {
        Optional<Student> studentOptional = studentRepository.findById(admissionId);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            StudentDto studentDto = mapToDto(student);
            return ResponseEntity.ok(studentDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    public ResponseEntity<StudentDto> addStudent(StudentDto student) {
        Student newStudent = new Student();
        newStudent.setFirstName(student.getFirstName());
        newStudent.setLastName(student.getLastName());
        newStudent.setGender(student.getGender());
        newStudent.setStream(student.getStream());
        newStudent.setAdmissionYear(student.getAdmissionYear());
        newStudent.setPosition(student.getPosition());
        newStudent.setEmail(student.getEmail());
        newStudent.setRole(student.getRole());
        studentRepository.save(newStudent);
        return ResponseEntity.ok(student);

    }

    public ResponseEntity<StudentDto> updateStudent(Long admissionId, StudentDto studentDto) {
        // Find the student by admissionId
        Optional<Student> existingStudent = studentRepository.findById(admissionId);

        if (existingStudent.isPresent()) {

            Student studentToUpdate = existingStudent.get();
            studentToUpdate.setFirstName(studentDto.getFirstName());
            studentToUpdate.setLastName(studentDto.getLastName());
            studentToUpdate.setGender(studentDto.getGender());
            studentToUpdate.setStream(studentDto.getStream());
            studentToUpdate.setAdmissionYear(studentDto.getAdmissionYear());
            studentToUpdate.setPosition(studentDto.getPosition());
            studentToUpdate.setEmail(studentDto.getEmail());
            studentToUpdate.setRole(studentDto.getRole());

            // Save the updated student
            Student updatedStudent = studentRepository.save(studentToUpdate);

            // Convert the updated student entity to a DTO
            StudentDto updatedStudentDto = mapToDto(updatedStudent);;

            return ResponseEntity.ok(updatedStudentDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<StudentDto> deleteStudent(Long admissionid) {
        Optional<Student> existingStudent = studentRepository.findById(admissionid);
        if (existingStudent.isPresent()) {
            studentRepository.delete(existingStudent.get());
        }
       return ResponseEntity.notFound().build();
    }
}