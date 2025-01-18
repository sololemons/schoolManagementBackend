package com.SCHMA.SchoolManagement.REPOSITORY;

import com.SCHMA.SchoolManagement.ENTITY.Staff;
import com.SCHMA.SchoolManagement.ENTITY.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository  extends JpaRepository<Staff, Long> {

    Optional <Staff> findByEmail(String email);


    @Query("SELECT s FROM Staff s WHERE " +
            "(:staffID IS NULL OR s.staffID = :staffID) AND " +
            "(:admissionYear IS NULL OR s.admissionYear = :admissionYear) AND " +
            "(:department IS NULL OR s.department = :department)")
    Page<Staff> findByFilters(@Param("staffID") Long staffID,
                                @Param("admissionYear") Long admissionYear,
                                @Param("department") String department,
                                                      Pageable pageable);
}

