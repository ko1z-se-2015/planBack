package com.example.volunteer.repositories;

import com.example.volunteer.entities.Degree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DegreeRepo extends JpaRepository<Degree, Long> {
    @Query("SELECT d FROM Degree d WHERE d.nameRu = :name OR d.nameKz = :name OR d.nameEn = :name")
    Degree findByName(@Param("name") String name);
}
