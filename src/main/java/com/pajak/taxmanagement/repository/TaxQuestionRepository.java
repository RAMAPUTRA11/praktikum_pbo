package com.pajak.taxmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pajak.taxmanagement.model.TaxQuestion;

@Repository
public interface TaxQuestionRepository extends JpaRepository<TaxQuestion, Long> {
    // Ambil pertanyaan berdasarkan kategori pajak tertentu
    List<TaxQuestion> findByCategoryId(Long categoryId);
}