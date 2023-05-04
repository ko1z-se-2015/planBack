package com.example.volunteer.services;

import com.example.volunteer.entities.*;
import com.example.volunteer.modules.*;
import com.example.volunteer.repositories.*;
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
    private final ResearchWorkRepo researchWorkRepo;
    private final EducationalWorkRepo educationalWorkRepo;
    private final SocialWorkRepo socialWorkRepo;

    public void createPlan(Plan plan){
        planRepo.save(plan);
    }
    public List<Plan> getPlanByCreatedBy(User user){
        return planRepo.findAllByCreatedBy(user);
    }

    public List<Plan> getPlanByCreatedFor(User user){
        return planRepo.findAllByCreatedFor(user);
    }

    public void addAcademicWorks(AddAcademicWork addAcademicWork) {
        Plan myPlan = planRepo.getById(addAcademicWork.getIdPlan());
        List<AcademicWork> myAcademicWork = myPlan.getAcademicWorks();
        AcademicWork newAcademicwork = academicWorkRepo.save(new AcademicWork(addAcademicWork.getNameOfDiscipline(), addAcademicWork.getCourse(), addAcademicWork.getTrimester(), addAcademicWork.getGroups(), addAcademicWork.getLecturesPlan(), addAcademicWork.getLecturesFact(), addAcademicWork.getPracticesPlan(), addAcademicWork.getPracticesFact(), addAcademicWork.getHoursPlan(), addAcademicWork.getHoursFact(), addAcademicWork.getTotalPlan(), addAcademicWork.getTotalFact()));
        myAcademicWork.add(newAcademicwork);
        myPlan.setAcademicWorks(myAcademicWork);
        planRepo.save(myPlan);
    }

    public Plan getLastMyPlan(User user){
        List<Plan> plans = planRepo.findAllByCreatedByOrderByIdDesc(user);
        Plan plan =  plans.get(0);
        return plan;
    }

    public void addAcademicMethods(AddAcademicMethod addAcademicMethod) {
        Plan myPlan = planRepo.getById(addAcademicMethod.getIdPlan());
        List<AcademicMethod> myAcademicMethods = myPlan.getAcademicMethods();
        AcademicMethod newAcademicMethod = academicMethodRepo.save(new AcademicMethod(addAcademicMethod.getDiscipline(), addAcademicMethod.getNameWork(), addAcademicMethod.getDeadlines(), addAcademicMethod.getInfoImplementation(), addAcademicMethod.getComment()));
        myAcademicMethods.add(newAcademicMethod);
        myPlan.setAcademicMethods(myAcademicMethods);
        planRepo.save(myPlan);
    }

    public void addResearchWork(AddReseachWork addReseachWork) {
        Plan myPlan = planRepo.getById(addReseachWork.getIdPlan());
        List<ResearchWork> myResearchWork = myPlan.getResearchWorks();
        ResearchWork newResearchWork;
        if (addReseachWork.getTypeOfTheWork() == 1 || addReseachWork.getTypeOfTheWork() == 2 || addReseachWork.getTypeOfTheWork() == 3) {
            newResearchWork = researchWorkRepo.save(new ResearchWork((addReseachWork.getTypeOfTheWork()), addReseachWork.getText1(), addReseachWork.getText2(), addReseachWork.getText3(), addReseachWork.getDeadlines(), addReseachWork.getInformationOnImplementation(), addReseachWork.getResults(), addReseachWork.getComments()));
        } else {
            newResearchWork = researchWorkRepo.save(new ResearchWork(addReseachWork.getTypeOfTheWork(), addReseachWork.getText1(), addReseachWork.getDeadlines(), addReseachWork.getInformationOnImplementation(), addReseachWork.getResults(), addReseachWork.getComments()));
        }
        myResearchWork.add(newResearchWork);
        myPlan.setResearchWorks(myResearchWork);
        planRepo.save(myPlan);
    }

    public void addEducationalWork(AddEducationalWork addEducationalWork) {
        Plan myPlan = planRepo.getById(addEducationalWork.getIdPlan());
        List<EducationalWork> myEducationalWork = myPlan.getEducationalWorks();
        EducationalWork newEducationalWork = educationalWorkRepo.save(new EducationalWork(addEducationalWork.getNameOfTheWork(), addEducationalWork.getDeadlines(), addEducationalWork.getInfoImplementation(), addEducationalWork.getResults(), addEducationalWork.getComments()));
        myEducationalWork.add(newEducationalWork);
        myPlan.setEducationalWorks(myEducationalWork);
        planRepo.save(myPlan);
    }

    public void addSocialWork(AddSocialWork addSocialWork) {
        Plan myPlan = planRepo.getById(addSocialWork.getIdPlan());
        List<SocialWork> mySocialWork = myPlan.getSocialWorks();
        SocialWork newSocialWork = socialWorkRepo.save(new SocialWork(addSocialWork.getNameOfTheWork(), addSocialWork.getDeadlines(), addSocialWork.getInfoImplementation(), addSocialWork.getResults(), addSocialWork.getComments()));
        mySocialWork.add(newSocialWork);
        myPlan.setSocialWorks(mySocialWork);
        planRepo.save(myPlan);
    }

}
