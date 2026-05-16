package com.pajak.taxmanagement.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pajak.taxmanagement.model.TaxCategory;
import com.pajak.taxmanagement.model.TaxTransaction;
import com.pajak.taxmanagement.model.User;
import com.pajak.taxmanagement.repository.TaxCategoryRepository;
import com.pajak.taxmanagement.repository.TaxTransactionRepository;
import com.pajak.taxmanagement.repository.UserRepository;

@Service
public class TaxService {

    @Autowired private TaxTransactionRepository transactionRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private TaxCategoryRepository categoryRepository;

    public void processTaxCalculation(Long userId, Long categoryId, Double amount) {
        double taxResult = 0;
        // Logika tarif pajak sederhana
        if (categoryId == 1) taxResult = amount * 0.05;      // Penghasilan
        else if (categoryId == 2) taxResult = amount * 0.02; // Kendaraan
        else if (categoryId == 3) taxResult = amount * 0.001;// Properti
        else if (categoryId == 4) taxResult = amount * 0.11; // Belanja
        else taxResult = amount * 0.10;
        
        TaxTransaction transaction = new TaxTransaction();
        User user = userRepository.findById(userId).orElse(null);
        TaxCategory category = categoryRepository.findById(categoryId).orElse(null);

        if (user != null && category != null) {
            transaction.setUser(user);
            transaction.setCategory(category);
            transaction.setTaxableAmount(amount);
            transaction.setCalculatedTax(taxResult);
            transaction.setStatus("PAID");
            transaction.setCreatedAt(LocalDateTime.now());
            transactionRepository.save(transaction);
        }
    }

    public void completeOnboarding(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setOnboarded(true); // Update field di database
            userRepository.save(user);
        }
    }
}