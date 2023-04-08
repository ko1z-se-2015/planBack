package com.example.volunteer.services;

import com.example.volunteer.entities.Plan;
import com.example.volunteer.entities.User;
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

    public void createPlan(Plan plan){
        planRepo.save(plan);
    }
    public List<Plan> getPlanByCreatedBy(User user){
        return planRepo.findAllByCreatedBy(user);
    }

    public List<Plan> getPlanByCreatedFor(User user){
        return planRepo.findAllByCreatedFor(user);
    }

}
