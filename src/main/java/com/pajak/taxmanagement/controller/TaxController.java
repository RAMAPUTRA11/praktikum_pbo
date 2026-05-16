package com.pajak.taxmanagement.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pajak.taxmanagement.model.TaxTransaction;
import com.pajak.taxmanagement.repository.TaxTransactionRepository;
import com.pajak.taxmanagement.service.TaxService;

@RestController
@RequestMapping("/api/tax")
@CrossOrigin(origins = "*")
public class TaxController {

    @Autowired
    private TaxService taxService;

    @Autowired
    private TaxTransactionRepository transactionRepository;

    // Mendapatkan semua transaksi (untuk Admin)
    @GetMapping("/all")
    public List<TaxTransaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    /**
     * Menambahkan endpoint untuk mendapatkan transaksi spesifik per user
     * Ini yang digunakan oleh dashboard.html untuk mengisi tabel riwayat
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaxTransaction>> getTransactionsByUserId(@PathVariable Long userId) {
        try {
            List<TaxTransaction> transactions = transactionRepository.findByUserId(userId);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/onboarding-complete/{userId}")
    public ResponseEntity<?> completeOnboarding(@PathVariable Long userId) {
        try {
            taxService.completeOnboarding(userId);
            return ResponseEntity.ok(Collections.singletonMap("status", "success"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    /**
     * Digunakan oleh file tax-income.html, tax-vehicle.html, dll
     * untuk mengirim data kalkulasi pajak baru
     */
    @PostMapping("/calculate")
    public ResponseEntity<?> calculate(@RequestBody Map<String, Object> payload) {
        try {
            // Konversi manual untuk keamanan tipe data JSON
            Long userId = Long.valueOf(payload.get("userId").toString());
            Long categoryId = Long.valueOf(payload.get("categoryId").toString());
            Double amount = Double.valueOf(payload.get("amount").toString());

            // Memproses logika bisnis melalui Service
            taxService.processTaxCalculation(userId, categoryId, amount);
            
            return ResponseEntity.ok(Collections.singletonMap("status", "success"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Collections.singletonMap("error", "Gagal menyimpan data pajak: " + e.getMessage())
            );
        }
    }
}