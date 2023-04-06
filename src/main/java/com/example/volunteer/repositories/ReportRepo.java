package com.example.volunteer.repositories;


import com.example.volunteer.entities.Report;
import com.example.volunteer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepo extends JpaRepository<Report,Long> {

    List<Report> findAllBySendTo(User user);
    List<Report> findAllBySendBy(User user);
}
