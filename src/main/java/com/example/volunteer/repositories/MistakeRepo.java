package com.example.volunteer.repositories;


import com.example.volunteer.entities.Mistakes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MistakeRepo extends JpaRepository<Mistakes,Long> {
}
