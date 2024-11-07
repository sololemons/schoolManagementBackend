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
@Table(name = "Admin")
public class Admin implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    @Column(name = "id")
    private int id;

    @JsonProperty("firstName")
    @Column(name = "firstName")
    private String firstName;


    @JsonProperty("lastName")
    @Column(name = "lastName")
    private String lastName;


    @JsonProperty("password")
    @Column(name = "password")
    private String password;

    @JsonProperty("email")
    @Column(name = "email", unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @JsonProperty("role")
    @Column(name = "role")
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
