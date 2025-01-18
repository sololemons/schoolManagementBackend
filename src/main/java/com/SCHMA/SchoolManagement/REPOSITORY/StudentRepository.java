package com.SCHMA.SchoolManagement.REPOSITORY;

import com.SCHMA.SchoolManagement.ENTITY.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student>findByEmail(String email);

    @Query("SELECT s FROM Student s WHERE " +
            "(:AdmissionId IS NULL OR s.AdmissionId = :AdmissionId) AND " +
            "(:AdmissionYear IS NULL OR s.AdmissionYear = :AdmissionYear) AND " +
            "(:Stream IS NULL OR s.Stream = :Stream)")
    Page<Student> findByFilters(@Param("AdmissionId") Long AdmissionId,
                                @Param("AdmissionYear") Integer AdmissionYear,
                                @Param("Stream") String Stream, Pageable pageable);
}
