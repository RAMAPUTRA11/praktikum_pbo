package com.pajak.taxmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pajak.taxmanagement.model.TaxTransaction;

@Repository
public interface TaxTransactionRepository extends JpaRepository<TaxTransaction, Long> {
    // TAMBAHKAN BARIS INI:
    List<TaxTransaction> findByUserId(Long userId);
}