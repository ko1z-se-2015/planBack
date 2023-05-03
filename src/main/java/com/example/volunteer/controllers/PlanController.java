package com.example.volunteer.controllers;


import com.example.volunteer.entities.AcademicMethod;
import com.example.volunteer.entities.AcademicWork;
import com.example.volunteer.entities.Plan;
import com.example.volunteer.entities.User;
import com.example.volunteer.modules.AddAcademicMethod;
import com.example.volunteer.modules.AddAcademicWork;
import com.example.volunteer.modules.AddReseachWork;
import com.example.volunteer.services.PlanService;
import com.example.volunteer.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/plan")
public class PlanController {

    private final PlanService planService;
    private final UserService userService;

    public PlanController(PlanService planService, UserService userService) {
        this.planService = planService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity createPlan(@RequestHeader(value="Authorization") String authorization, @RequestBody Plan bodyPlan){
        Plan plan = bodyPlan;
        User user = userService.getByToken(authorization);
        plan.setCreatedBy(user);
        planService.createPlan(plan);
        return new ResponseEntity("plan created", HttpStatus.CREATED);
    }

    @GetMapping("/get-my-plans")
    public ResponseEntity getMyPlans(@RequestHeader(value="Authorization") String authorization){
        User user = userService.getByToken(authorization);
        return ResponseEntity.ok(planService.getPlanByCreatedBy(user));
    }

    @GetMapping("/get-last-plan")
    public ResponseEntity getLastMyPlan(@RequestHeader(value="Authorization") String authorization){
        User user = userService.getByToken(authorization);
        Plan plan = planService.getLastMyPlan(user);
        return ResponseEntity.ok(plan);
    }

    @GetMapping("/get-plans-to-me")
    public ResponseEntity getPlansToMee(@RequestHeader(value="Authorization") String authorization){
        User user = userService.getByToken(authorization);
        return ResponseEntity.ok(planService.getPlanByCreatedFor(user));
    }

    @PostMapping("/add-academic-work")
    public ResponseEntity addAcademicWork(@RequestHeader(value="Authorization") String authorization, @RequestBody AddAcademicWork addAcademicWork){
        planService.addAcademicWorks(addAcademicWork);
        return new ResponseEntity("academic work added", HttpStatus.CREATED);
    }

    @PostMapping("/add-academic-methods")
    public ResponseEntity addAcademicWork(@RequestHeader(value="Authorization") String authorization, @RequestBody AddAcademicMethod addAcademicMethod){
        planService.addAcademicMethods(addAcademicMethod);
        return new ResponseEntity("academic method added", HttpStatus.CREATED);
    }

    @PostMapping("/add-research-work")
    public ResponseEntity addResearchWork(@RequestHeader(value="Authorization") String authorization, @RequestBody AddReseachWork addReseachWork){
        planService.addResearchWork(addReseachWork);
        return new ResponseEntity("research work added", HttpStatus.CREATED);
    }
}
