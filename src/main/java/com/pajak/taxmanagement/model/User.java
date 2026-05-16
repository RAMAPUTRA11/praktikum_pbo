package com.pajak.taxmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    
    private String password;
    private String fullName;
    private String photoPath = "default.png";
    private String role = "USER"; // Default role: USER atau ADMIN
    private boolean onboarded = false;

    // Tambahan Field untuk Biodata Onboarding
    private String address;
    private String phoneNumber;
    private String jobTitle;

    /* Note: Karena lu pake @Data dari Lombok, 
       Getter, Setter, dan Constructor udah otomatis dibuat di belakang layar.
    */
}