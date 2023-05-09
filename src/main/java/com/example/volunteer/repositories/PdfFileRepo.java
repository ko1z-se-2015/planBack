package com.example.volunteer.repositories;

import com.example.volunteer.entities.PdfFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PdfFileRepo extends JpaRepository<PdfFile, Long> {
    PdfFile findPdfFileByKpi_Id(Long id);
}
