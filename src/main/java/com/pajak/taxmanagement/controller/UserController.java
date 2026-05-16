package com.pajak.taxmanagement.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pajak.taxmanagement.model.TaxTransaction;
import com.pajak.taxmanagement.model.User;
import com.pajak.taxmanagement.repository.TaxTransactionRepository;
import com.pajak.taxmanagement.repository.UserRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaxTransactionRepository transactionRepository;

    // 1. AMBIL SEMUA USER KECUALI ADMIN
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll().stream()
                .filter(user -> !user.getUsername().equalsIgnoreCase("admin"))
                .collect(Collectors.toList());
    }

    // 2. AMBIL TRANSAKSI BERDASARKAN USER ID (Buat Detail History per User)
    @GetMapping("/users/{userId}/transactions")
    public List<TaxTransaction> getUserTransactions(@PathVariable Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    // 3. AMBIL SEMUA TRANSAKSI (Tambahan biar menu Transactions di Admin bisa diakses)
    @GetMapping("/transactions")
    public List<TaxTransaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // 4. UPDATE USER
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User user = userRepository.findById(id).orElseThrow();
        user.setFullName(userDetails.getFullName());
        user.setJobTitle(userDetails.getJobTitle());
        return userRepository.save(user);
    }

    // 5. HAPUS USER
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}