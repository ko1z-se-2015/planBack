package com.example.volunteer.services;

import com.example.volunteer.entities.Position;
import com.example.volunteer.repositories.PositionRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PositionService {
    private final PositionRepo positionRepo;

    public List<Position> getAllPositions() {
        return positionRepo.findAll();
    }

    public Position getPositionByName(String name) {
        return positionRepo.findByName(name);
    }

    public void savePosition(Position position) {
        positionRepo.save(position);
    }

    public void deletePosition(Position position) {
        positionRepo.delete(position);
    }
}
