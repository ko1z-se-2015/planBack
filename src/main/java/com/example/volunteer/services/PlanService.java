package com.example.volunteer.services;

import com.example.volunteer.entities.AcademicMethod;
import com.example.volunteer.entities.AcademicWork;
import com.example.volunteer.entities.Plan;
import com.example.volunteer.entities.User;
import com.example.volunteer.modules.AddAcademicMethod;
import com.example.volunteer.repositories.AcademicMethodRepo;
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
public class PlanService {

    private final PlanRepo planRepo;
    private final AcademicWorkRepo academicWorkRepo;
    private final AcademicMethodRepo academicMethodRepo;

    public void createPlan(Plan plan){
        planRepo.save(plan);
    }
    public List<Plan> getPlanByCreatedBy(User user){
        return planRepo.findAllByCreatedBy(user);
    }

    public List<Plan> getPlanByCreatedFor(User user){
        return planRepo.findAllByCreatedFor(user);
    }

    public void addAcademicWorks(Plan plan, AcademicWork academicWork) {
        Plan myPlan = planRepo.getById(plan.getId());
        List<AcademicWork> myAcademicWork = myPlan.getAcademicWorks();
        AcademicWork newAcademicwork = academicWorkRepo.save(academicWork);
        myAcademicWork.add(newAcademicwork);
        myPlan.setAcademicWorks(myAcademicWork);
        planRepo.save(myPlan);
    }

    public void addAcademicMethods(AddAcademicMethod addAcademicMethod) {
        System.out.println(addAcademicMethod.getIdPlan());
        Plan myPlan = planRepo.getById(addAcademicMethod.getIdPlan());
        List<AcademicMethod> myAcademicMethods = myPlan.getAcademicMethods();
        AcademicMethod newAcademicMethod = academicMethodRepo.save(new AcademicMethod(addAcademicMethod.getDiscipline(), addAcademicMethod.getNameWork(), addAcademicMethod.getDeadlines(), addAcademicMethod.getInfoImplementation(), addAcademicMethod.getComment()));
        myAcademicMethods.add(newAcademicMethod);
        myPlan.setAcademicMethods(myAcademicMethods);
        planRepo.save(myPlan);
    }

}
