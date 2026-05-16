package com.pajak.taxmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pajak.taxmanagement.model.TaxTransaction;
import com.pajak.taxmanagement.model.User;
import com.pajak.taxmanagement.repository.TaxTransactionRepository;
import com.pajak.taxmanagement.repository.UserRepository;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaxTransactionRepository transactionRepository;

    // 1. Ambil semua User untuk Dashboard
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 2. Ambil semua Transaksi Pajak (Audit Log)
    @GetMapping("/transactions")
    public List<TaxTransaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // 3. Hapus Transaksi (Fitur Penting untuk Tombol Delete di Audit Log)
    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
            return ResponseEntity.ok().body("{\"message\": \"Transaksi berhasil dihapus\"}");
        }
        return ResponseEntity.status(404).body("{\"message\": \"Transaksi tidak ditemukan\"}");
    }

    // 4. Hapus User
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().body("{\"message\": \"User berhasil dihapus\"}");
        }
        return ResponseEntity.status(404).body("{\"message\": \"User tidak ditemukan\"}");
    }
}