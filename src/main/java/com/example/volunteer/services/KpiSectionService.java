package com.example.volunteer.services;

import com.example.volunteer.entities.Degree;
import com.example.volunteer.entities.Position;
import com.example.volunteer.entities.kpi_sections.KpiSection;
import com.example.volunteer.repositories.DegreeRepo;
import com.example.volunteer.repositories.KpiSectionRepo;
import com.example.volunteer.repositories.PositionRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class KpiSectionService {

    private final KpiSectionRepo kpiSectionRepo;
    private final PositionRepo positionRepo;
    private final DegreeRepo degreeRepo;

    public List<KpiSection> getAllKpiSections() {
        return kpiSectionRepo.findAll();
    }

    public List<KpiSection> getByPositionNameAndDegreeName(String positionName, String degreeName) {
        Position position = positionRepo.findByName(positionName);
        Degree degree = degreeRepo.findByName(degreeName);

        return kpiSectionRepo.findByPositionAndDegree(position, degree);
    }

    public KpiSection getByPositionNameAndDegreeNameAndOptionsContains(String positionName, String degreeName, String option) {
        Position position = positionRepo.findByName(positionName);
        Degree degree = degreeRepo.findByName(degreeName);

        return kpiSectionRepo.getByPositionAndDegreeAndOptionsContains(position, degree, option);
    }

    public KpiSection getByPositionNameAndDegreeNameAndSectionNumber (String positionName, String degreeName, int sectionNumber) {
        Position position = positionRepo.findByName(positionName);
        Degree degree = degreeRepo.findByName(degreeName);

        return kpiSectionRepo.findByPositionAndDegreeAndSectionNumber(position, degree, sectionNumber);
    }

    public KpiSection save(KpiSection kpiSection, String positionName, String degreeName) {
        Position position = positionRepo.findByName(positionName);
        Degree degree = degreeRepo.findByName(degreeName);

        kpiSection.setPosition(position);
        kpiSection.setDegree(degree);

        return kpiSectionRepo.save(kpiSection);
    }

    public void delete(KpiSection kpiSection) {
        kpiSectionRepo.delete(kpiSection);
    }

}
