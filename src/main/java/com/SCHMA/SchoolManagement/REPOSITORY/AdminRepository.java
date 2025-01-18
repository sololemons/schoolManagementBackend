package com.SCHMA.SchoolManagement.REPOSITORY;

import com.SCHMA.SchoolManagement.ENTITY.Admin;
import com.SCHMA.SchoolManagement.ENTITY.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface AdminRepository  extends JpaRepository<Admin, Integer> {

    Optional<Admin> findByEmail(String email);
}