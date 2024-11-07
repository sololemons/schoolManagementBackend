package com.SCHMA.SchoolManagement.ENTITY;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Student")
public class Student implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonProperty("AdmissionId")
    @Column(name = "AdmissionId")
    private Long AdmissionId;

    @JsonProperty("Password")
    @Column(name = "Password")
    private String password;

    @JsonProperty("firstName")
    @Column(name = "firstName")
    private String firstName;

    @JsonProperty("lastName")
    @Column(name = "lastName")
    private String lastName;

    @JsonProperty("AdmissionYear")
    @Column(name = "AdmissionYear")
    private Long AdmissionYear;

    @JsonProperty("Gender")
    @Column(name = "Gender")
    private String Gender;

    @JsonProperty("Stream")
    @Column(name = "Stream")
    private String Stream;

    @Column(nullable = true)
    @JsonProperty("Position")
    private String Position;

    @Column(name = "email", unique = true)
    @JsonProperty("email")
    private String email;

    @Enumerated(EnumType.STRING)
    @JsonProperty("Role")
    private Role role;

    // Implementing UserDetails interface methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
