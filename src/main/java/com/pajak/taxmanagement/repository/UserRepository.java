package com.pajak.taxmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pajak.taxmanagement.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username); // Ini kunci buat nyari user di DB
}