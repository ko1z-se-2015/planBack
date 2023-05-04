package com.example.volunteer.repositories;

import com.example.volunteer.entities.AcademicMethod;
import com.example.volunteer.entities.EducationalWork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationalWorkRepo extends JpaRepository<EducationalWork,Long> {

}
