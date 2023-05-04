package com.example.volunteer.services;

import com.example.volunteer.entities.EducationalWork;
import com.example.volunteer.entities.Plan;
import com.example.volunteer.repositories.EducationalWorkRepo;
import com.example.volunteer.repositories.PlanRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EducationalWorkService {

    private final EducationalWorkRepo educationalWorkRepo;
    private final PlanRepo planRepo;

    public void saveEducationalWork(EducationalWork EducationalWork){
        educationalWorkRepo.save(EducationalWork);
    }

    public void deleteEducationalWorkById(Long id){
        EducationalWork educationalWork = educationalWorkRepo.getById(id);
        Plan plan = planRepo.findByEducationalWorksContaining(educationalWork);
        plan.getEducationalWorks().remove(educationalWork);
        planRepo.save(plan);
        educationalWorkRepo.delete(educationalWork);
    }


    public EducationalWork getEducationalWorkById(Long id){
        return educationalWorkRepo.getById(id);
    }

    public void updateEducationalWork(EducationalWork educationalWork){
        EducationalWork toUpdate = educationalWorkRepo.getById(educationalWork.getId());
        toUpdate.setNameOfTheWork(educationalWork.getNameOfTheWork());
        toUpdate.setDeadlines(educationalWork.getDeadlines());
        toUpdate.setInfoImplementation(educationalWork.getInfoImplementation());
        toUpdate.setResults(educationalWork.getResults());
        toUpdate.setComments(educationalWork.getComments());
        educationalWorkRepo.save(toUpdate);
    }
}
