package com.example.volunteer.controllers;

import com.example.volunteer.entities.KPI;
import com.example.volunteer.entities.Plan;
import com.example.volunteer.entities.User;
import com.example.volunteer.modules.*;
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

    @GetMapping("/get-plans-to-me-by-status")
    public ResponseEntity<List<Plan>> getPlansToMeByStatus(@RequestHeader(value="Authorization") String authorization,
                                                           @RequestParam String status) {
        User user = userService.getByToken(authorization);
        List<Plan> plans = planService.getPlansByCreatedForAndStatus(user, status);

        return ResponseEntity.ok(plans);
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

    @PostMapping("/add-educational-work")
    public ResponseEntity addEducationalWork(@RequestHeader(value="Authorization") String authorization, @RequestBody AddEducationalWork addEducationalWork){
        planService.addEducationalWork(addEducationalWork);
        return new ResponseEntity("educational work added", HttpStatus.CREATED);
    }

    @PostMapping("/add-social-work")
    public ResponseEntity addSocialWork(@RequestHeader(value="Authorization") String authorization, @RequestBody AddSocialWork addSocialWork){
        planService.addSocialWork(addSocialWork);
        return new ResponseEntity("social work added", HttpStatus.CREATED);
    }

    @PostMapping("/add-kpi")
    public ResponseEntity addKPI(@RequestHeader(value="Authorization") String authorization, @RequestParam(name = "id") Long planId,@RequestParam(name = "idSection") Long idSection, @RequestBody KPI kpi){
        planService.addKpi(planId, idSection,kpi);
        return new ResponseEntity("kpi added", HttpStatus.CREATED);
    }

    @GetMapping("/get-by-id")
    public ResponseEntity getPlanById(@RequestHeader(value="Authorization") String authorization, @RequestParam(name="id") Integer id){
        Plan plan = planService.getPlanById(Long.valueOf(id));
        return ResponseEntity.ok(plan);
    }

    @PostMapping("/change-year")
    public ResponseEntity changeYear(@RequestHeader(value="Authorization") String authorization, @RequestBody ChangeYearModule changeYearModule){
        Plan plan = planService.getPlanById(changeYearModule.getPlan().getId());
        planService.changeYear(plan, changeYearModule.getYear());
        return new ResponseEntity("year changed", HttpStatus.OK);
    }
}
