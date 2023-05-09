package com.example.volunteer.controllers;

import com.example.volunteer.entities.kpi_sections.KpiSection;
import com.example.volunteer.services.KpiSectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/kpi-section")
public class KpiSectionController {

    private final KpiSectionService kpiSectionService;

    public KpiSectionController(KpiSectionService kpiSectionService) {
        this.kpiSectionService = kpiSectionService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<KpiSection>> getAllKpiSections(@RequestParam String positionName,
                                                              @RequestParam String degreeName) {
        List<KpiSection> kpiSections = kpiSectionService.getByPositionNameAndDegreeName(positionName, degreeName);

        return ResponseEntity.ok(kpiSections);
    }

    @GetMapping("/get")
    public ResponseEntity<KpiSection> getKpiSection(@RequestParam String positionName,
                                                    @RequestParam String degreeName,
                                                    @RequestParam int sectionNumber) {
        KpiSection kpiSection = kpiSectionService.getByPositionNameAndDegreeNameAndSectionNumber(positionName, degreeName, sectionNumber);

        return ResponseEntity.ok(kpiSection);
    }
}
