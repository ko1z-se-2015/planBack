package com.example.volunteer.repositories;

import com.example.volunteer.entities.AcademicWork;
import com.example.volunteer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcademicWorkRepo extends JpaRepository<AcademicWork,Long> {

    List<AcademicWork> findAcademicWorksByCreatedBy(User user);
}
