package com.example.volunteer.repositories;

import com.example.volunteer.entities.KPI;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KpiRepo extends JpaRepository<KPI, Long> {

}
