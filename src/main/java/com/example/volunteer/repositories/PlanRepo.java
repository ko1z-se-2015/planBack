package com.example.volunteer.repositories;


import com.example.volunteer.entities.AcademicMethod;
import com.example.volunteer.entities.AcademicWork;
import com.example.volunteer.entities.Plan;
import com.example.volunteer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepo extends JpaRepository<Plan,Long> {

    List<Plan> findAllByCreatedBy(User user);
    List<Plan> findAllByCreatedFor(User user);

    List<Plan> findAllByCreatedByOrderByIdDesc(User user);

    Plan findByAcademicWorksContaining(AcademicWork academicWork);
    Plan findByAcademicMethodsContaining(AcademicMethod academicMethod);
}
