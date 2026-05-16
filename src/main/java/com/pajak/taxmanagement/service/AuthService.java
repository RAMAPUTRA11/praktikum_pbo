package com.pajak.taxmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pajak.taxmanagement.model.User;
import com.pajak.taxmanagement.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User login(User loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            return user;
        }
        return null;
    }

    public String register(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return "Error: Username sudah terdaftar!";
        }
        // Default values
        user.setRole(user.getRole() == null ? "USER" : user.getRole());
        user.setOnboarded(false);
        userRepository.save(user);
        return "Registrasi Berhasil!";
    }

    // METHOD BARU: Update Profil/Biodata saat Onboarding
    public void updateProfile(Long userId, User profileData) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setAddress(profileData.getAddress());
            user.setPhoneNumber(profileData.getPhoneNumber());
            user.setJobTitle(profileData.getJobTitle());
            userRepository.save(user);
        }
    }
}