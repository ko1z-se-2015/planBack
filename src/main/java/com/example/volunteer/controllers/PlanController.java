package com.example.volunteer.controllers;


import com.example.volunteer.entities.Plan;
import com.example.volunteer.entities.User;
import com.example.volunteer.services.PlanService;
import com.example.volunteer.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get-plans-to-me")
    public ResponseEntity getPlansToMee(@RequestHeader(value="Authorization") String authorization){
        User user = userService.getByToken(authorization);
        return ResponseEntity.ok(planService.getPlanByCreatedFor(user));
    }
}
