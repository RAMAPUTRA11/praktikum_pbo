package com.pajak.taxmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pajak.taxmanagement.model.TaxCategory;

@Repository
public interface TaxCategoryRepository extends JpaRepository<TaxCategory, Long> {
}