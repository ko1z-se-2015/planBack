package com.example.volunteer.repositories;

import com.example.volunteer.entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PositionRepo extends JpaRepository<Position, Long> {
    @Query("SELECT p FROM Position p WHERE p.nameRu = :name OR p.nameKz = :name OR p.nameEn = :name")
    Position findByName(@Param("name") String name);
}
