package com.pajak.taxmanagement.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pajak.taxmanagement.model.User;
import com.pajak.taxmanagement.repository.UserRepository;
import com.pajak.taxmanagement.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        User user = authService.login(loginRequest);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(401).body("Username atau Password salah!");
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        String result = authService.register(user);
        if (result.contains("Error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update-profile/{userId}")
    public ResponseEntity<String> updateProfile(@PathVariable Long userId, @RequestBody User profileData) {
        try {
            authService.updateProfile(userId, profileData);
            return ResponseEntity.ok("Profil berhasil diperbarui!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Gagal update profil: " + e.getMessage());
        }
    }

    @PostMapping("/upload-photo/{userId}")
    public ResponseEntity<?> uploadPhoto(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File tidak ditemukan!");
            }

            // 1. Setup Folder (Gunakan path absolut agar langsung terbaca tanpa restart)
            String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/img/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs(); // Bikin folder otomatis kalau belum ada
            }

            // 2. Format Nama File
            String originalName = file.getOriginalFilename() != null ? file.getOriginalFilename() : "profile.png";
            String fileName = "user_" + userId + "_" + System.currentTimeMillis() + "_" + originalName.replace(" ", "_");
            Path path = Paths.get(uploadDir + fileName);

            // 3. Simpan File
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // 4. Update Database
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User tidak ditemukan"));
            
            user.setPhotoPath(fileName);
            userRepository.save(user);

            // 5. Kembalikan nama file
            return ResponseEntity.ok(fileName);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Gagal upload foto: " + e.getMessage());
        }
    }
}