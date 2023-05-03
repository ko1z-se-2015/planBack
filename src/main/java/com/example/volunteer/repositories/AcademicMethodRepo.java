package com.example.volunteer.repositories;

import com.example.volunteer.entities.AcademicMethod;
import com.example.volunteer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcademicMethodRepo extends JpaRepository<AcademicMethod,Long> {

}
