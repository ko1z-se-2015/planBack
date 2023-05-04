package com.example.volunteer.services;

import com.example.volunteer.entities.SocialWork;
import com.example.volunteer.entities.Plan;
import com.example.volunteer.repositories.SocialWorkRepo;
import com.example.volunteer.repositories.PlanRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SocialWorkService {

    private final SocialWorkRepo socialWorkRepo;
    private final PlanRepo planRepo;

    public void saveSocialWork(SocialWork socialWork){
        socialWorkRepo.save(socialWork);
    }

    public void deleteSocialWorkById(Long id){
        SocialWork socialWork = socialWorkRepo.getById(id);
        Plan plan = planRepo.findBySocialWorksContaining(socialWork);
        plan.getSocialWorks().remove(socialWork);
        planRepo.save(plan);
        socialWorkRepo.delete(socialWork);
    }


    public SocialWork getSocialWorkById(Long id){
        return socialWorkRepo.getById(id);
    }

    public void updateSocialWork(SocialWork socialWork){
        SocialWork toUpdate = socialWorkRepo.getById(socialWork.getId());
        toUpdate.setNameOfTheWork(socialWork.getNameOfTheWork());
        toUpdate.setDeadlines(socialWork.getDeadlines());
        toUpdate.setInfoImplementation(socialWork.getInfoImplementation());
        toUpdate.setResults(socialWork.getResults());
        toUpdate.setComments(socialWork.getComments());
        socialWorkRepo.save(toUpdate);
    }
}
