package com.example.volunteer.repositories;

import com.example.volunteer.entities.Degree;
import com.example.volunteer.entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.volunteer.entities.kpi_sections.KpiSection;

import java.util.List;

public interface KpiSectionRepo extends JpaRepository<KpiSection, Long> {
    List<KpiSection> findByPositionAndDegree(Position position, Degree degree);
    KpiSection findByPositionAndDegreeAndSectionNumber(Position position, Degree degree, int sectionNumber);
    KpiSection getByPositionAndDegreeAndOptionsContains(Position position, Degree degree, String option);
}
