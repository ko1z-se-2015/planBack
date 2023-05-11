package com.example.volunteer.repositories;


import com.example.volunteer.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepo extends JpaRepository<Plan,Long> {

    List<Plan> findAllByCreatedBy(User user);
    List<Plan> findAllByCreatedFor(User user);

    List<Plan> findAllByCreatedByOrderByIdDesc(User user);

    Plan findByAcademicWorksContaining(AcademicWork academicWork);
    Plan findByAcademicMethodsContaining(AcademicMethod academicMethod);
    Plan findByEducationalWorksContaining(EducationalWork educationalWork);
    Plan findBySocialWorksContaining(SocialWork socialWork);

    Plan findByResearchWorksContaining(ResearchWork researchWork);
    Plan findByKpisContaining(KPI kpi);

    List<Plan> getPlansByCreatedForAndStatusEquals(User user, String status);
}
