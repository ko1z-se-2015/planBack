package com.example.volunteer.services;

import com.example.volunteer.entities.KPI;
import com.example.volunteer.entities.PdfFile;
import com.example.volunteer.repositories.PdfFileRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PdfFileService {
   private final PdfFileRepo pdfFileRepo;

   public PdfFile getById(Long id){
       return pdfFileRepo.getById(id);
   }

   public PdfFile getByKpiId(Long id){
       return pdfFileRepo.findPdfFileByKpi_Id(id);
   }

   public void save(PdfFile pdfFile) {
       pdfFileRepo.save(pdfFile);
   }

   public void saveByParameters(String fileName, byte[] data, KPI kpi){
       PdfFile pdfFile = new PdfFile();
       pdfFile.setFileName(fileName);
       pdfFile.setData(data);
       pdfFile.setKpi(kpi);
       pdfFileRepo.save(pdfFile);
   }

    public void deleteById(Long id) {
        pdfFileRepo.deleteById(id);
    }
}
