package com.example.volunteer.services;

import com.example.volunteer.entities.Degree;
import com.example.volunteer.entities.Position;
import com.example.volunteer.repositories.DegreeRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DegreeService {
    private final DegreeRepo degreeRepo;

    public List<Degree> getAllDegrees() {
        return degreeRepo.findAll();
    }

    public Degree getDegreeByName(String name) {
        return degreeRepo.findByName(name);
    }

    public void saveDegree(Degree degree) {
        degreeRepo.save(degree);
    }

    public void deletePosition(Degree degree) {
        degreeRepo.delete(degree);
    }
}
