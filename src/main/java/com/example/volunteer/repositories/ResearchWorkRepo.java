package com.example.volunteer.repositories;

import com.example.volunteer.entities.AcademicWork;
import com.example.volunteer.entities.ResearchWork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResearchWorkRepo extends JpaRepository<ResearchWork,Long> {

}
