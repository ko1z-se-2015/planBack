package com.example.volunteer.services;

import com.example.volunteer.entities.AcademicWork;
import com.example.volunteer.entities.User;
import com.example.volunteer.repositories.AcademicWorkRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AcademicWorkService {

    private final AcademicWorkRepo academicWorkRepo;

    public void saveAcademicWork(AcademicWork academicWork){
        academicWorkRepo.save(academicWork);
    }

    public List<AcademicWork> getMyAcademicWorks(User user){
        return academicWorkRepo.findAcademicWorksByCreatedBy(user);
    }

    public void deleteAcademicWorkById(Long id){
        AcademicWork academicWork = academicWorkRepo.getById(id);
        academicWorkRepo.delete(academicWork);
    }

    public AcademicWork getAcademicWorkById(Long id){
        return academicWorkRepo.getById(id);
    }

    public void updateAcademicWork(AcademicWork academicWork){
        academicWorkRepo.save(academicWork);
    }
}
