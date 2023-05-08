package com.example.volunteer.repositories;

import com.example.volunteer.entities.Degree;
import com.example.volunteer.entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.volunteer.entities.kpi_sections.KpiSection;

public interface KpiSectionRepo extends JpaRepository<KpiSection, Long> {
    KpiSection findByPositionAndDegree(Position position, Degree degree);
}
