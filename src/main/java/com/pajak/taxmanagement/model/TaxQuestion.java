package com.pajak.taxmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tax_questions")
@Data
public class TaxQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private TaxCategory category; // Relasi ke jenis pajak (PPh, PBB, dll)

    private String questionText; // Isinya: "Berapa gaji anda?" atau "Berapa luas tanah?"
    private String inputType; // NUMBER atau TEXT
}