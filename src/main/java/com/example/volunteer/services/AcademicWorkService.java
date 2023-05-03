package com.example.volunteer.services;

import com.example.volunteer.entities.AcademicWork;
import com.example.volunteer.entities.Plan;
import com.example.volunteer.entities.User;
import com.example.volunteer.modules.UpdateAcademicWork;
import com.example.volunteer.repositories.AcademicWorkRepo;
import com.example.volunteer.repositories.PlanRepo;
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
    private final PlanRepo planRepo;

    public void saveAcademicWork(AcademicWork academicWork){
        academicWorkRepo.save(academicWork);
    }

    public void deleteAcademicWorkById(Long id){
        AcademicWork academicWork = academicWorkRepo.getById(id);
        Plan plan = planRepo.findByAcademicWorksContaining(academicWork);
        plan.getAcademicWorks().remove(academicWork);
        planRepo.save(plan);
        academicWorkRepo.delete(academicWork);
    }


    public AcademicWork getAcademicWorkById(Long id){
        return academicWorkRepo.getById(id);
    }

    public void updateAcademicWork(UpdateAcademicWork academicWork){
        AcademicWork toUpdate = academicWorkRepo.getById(academicWork.getId());
        toUpdate.setNameOfDiscipline(academicWork.getNameOfDiscipline());
        toUpdate.setCourse(academicWork.getCourse());
        toUpdate.setGroups(academicWork.getGroups());
        toUpdate.setTrimester(academicWork.getTrimester());
        toUpdate.setLecturesPlan(academicWork.getLecturesPlan());
        toUpdate.setLecturesFact(academicWork.getLecturesFact());
        toUpdate.setPracticesPlan(academicWork.getPracticesPlan());
        toUpdate.setPracticesFact(academicWork.getPracticesFact());
        toUpdate.setHoursPlan(academicWork.getHoursPlan());
        toUpdate.setHoursFact(academicWork.getHoursFact());
        toUpdate.setTotalPlan(academicWork.getTotalPlan());
        toUpdate.setTotalFact(academicWork.getTotalFact());
        academicWorkRepo.save(toUpdate);
    }
}
