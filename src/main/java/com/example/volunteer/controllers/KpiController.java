package com.example.volunteer.controllers;

import com.example.volunteer.entities.KPI;
import com.example.volunteer.entities.PdfFile;
import com.example.volunteer.modules.KPIsToDelete;
import com.example.volunteer.services.KpiService;
import com.example.volunteer.services.PdfFileService;
import com.example.volunteer.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/kpi")
public class KpiController {

    private final KpiService kpiService;
    private final UserService userService;
    private final PdfFileService pdfFileService;

    public KpiController(KpiService kpiService, UserService userService, PdfFileService pdfFileService) {
        this.kpiService = kpiService;
        this.userService = userService;
        this.pdfFileService = pdfFileService;
    }

    @PostMapping("/save")
    public ResponseEntity saveMyAcademicWork(@RequestHeader(value = "Authorization") String token,
                                             @RequestBody KPI kpi) {
        kpiService.saveKpi(kpi);
        return new ResponseEntity("kpi added", HttpStatus.CREATED);
    }

    @PostMapping("/delete-by-id")
    public ResponseEntity deleteAcademicWorkById(@RequestParam Long id) {
        kpiService.deleteById(id);
        return new ResponseEntity("kpi is deleted", HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity deleteAcademicWorks(@RequestBody KPIsToDelete kpis) {
        for(KPI item: kpis.getItems()){
            kpiService.deleteById(item.getId());
        }
        return new ResponseEntity("kpis are deleted", HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity  updateAcademicWorkById(@RequestBody KPI kpi){
        kpiService.update(kpi);
        return new ResponseEntity("kpi is updated", HttpStatus.OK);
    }

    @GetMapping("/download-supporting-document")
    public ResponseEntity<String> downloadPdfFile(HttpServletResponse response,
                                                  @RequestParam Long kpiId){
        PdfFile pdfFile = pdfFileService.getByKpiId(kpiId);
        byte[] data = pdfFile.getData();
        String fileName = pdfFile.getFileName();

        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
            response.setContentLength(data.length);
            response.getOutputStream().write(data);
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok("PDF file downloaded successfully.");
    }
}
