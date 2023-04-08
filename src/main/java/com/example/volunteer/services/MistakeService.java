package com.example.volunteer.services;

import com.example.volunteer.entities.Mistakes;
import com.example.volunteer.repositories.MistakeRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MistakeService {

    private final MistakeRepo mistakeRepo;

    public void createMistake(String name){
        Mistakes mistake = new Mistakes(name);
        mistakeRepo.save(mistake);
    }

    public List<Mistakes> getMistakes(){
        return mistakeRepo.findAll();
    }
}
