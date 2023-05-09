package com.example.volunteer.services;

import com.example.volunteer.entities.KPI;
import com.example.volunteer.entities.PdfFile;
import com.example.volunteer.entities.Plan;
import com.example.volunteer.repositories.KpiRepo;
import com.example.volunteer.repositories.KpiSectionRepo;
import com.example.volunteer.repositories.PdfFileRepo;
import com.example.volunteer.repositories.PlanRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class KpiService {

    private final KpiRepo kpiRepo;
    private final KpiSectionRepo kpiSectionRepo;
    private final PdfFileRepo pdfFileRepo;
    private final PlanRepo planRepo;

    public KPI getKpiById(Long id){
        return kpiRepo.getById(id);
    }

    public void saveKpi(Long kpiSectionId, KPI kpi){
        kpi.setKpiSection(kpiSectionRepo.findById(kpiSectionId).get());
        kpiRepo.save(kpi);
    }

    public void deleteById(Long id){
        KPI kpi = getKpiById(id);
        Plan plan = planRepo.findByKpisContaining(kpi);
        plan.getKpis().remove(kpi);
        planRepo.save(plan);
        kpiRepo.delete(kpi);
    }

    //TODO REDO if applicable
    public void update(KPI kpi){
        KPI toUpdate = getKpiById(kpi.getId());
        toUpdate.setNameOfTheWork(kpi.getNameOfTheWork());
        toUpdate.setDeadlines(kpi.getDeadlines());
        toUpdate.setInformationOnImplementation(kpi.getInformationOnImplementation());
        toUpdate.setResults(kpi.getResults());
        toUpdate.setComments(kpi.getComments());
        toUpdate.setPdfFile(kpi.getPdfFile());
        toUpdate.setPdfFileName(kpi.getPdfFileName());
        toUpdate.setPercentage(kpi.getPercentage());
        toUpdate.setAuthorsNumber(kpi.getAuthorsNumber());
        kpiRepo.save(toUpdate);
    }

}
