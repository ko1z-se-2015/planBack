package com.example.volunteer.controllers;

import com.example.volunteer.entities.AcademicWork;
import com.example.volunteer.entities.User;
import com.example.volunteer.services.AcademicWorkService;
import com.example.volunteer.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/academic-work")
public class AcademicWorkController {

    private final AcademicWorkService academicWorkService;

    private final UserService userService;

    public AcademicWorkController(AcademicWorkService academicWorkService, UserService userService) {
        this.academicWorkService = academicWorkService;
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity saveMyAcademicWork(@RequestHeader(value = "Authorization") String token, @RequestBody AcademicWork academicWork) {
        User user = userService.getByToken(token);
        academicWork.setCreatedBy(user);
        academicWorkService.saveAcademicWork(academicWork);
        return new ResponseEntity("academic work added", HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity getMyAcademicWorks(@RequestHeader(value = "Authorization") String token) {
        User user = userService.getByToken(token);
        List<AcademicWork> academicWorks = academicWorkService.getMyAcademicWorks(user);
        return ResponseEntity.ok(academicWorks);
    }

    @PostMapping("/delete-by-id")
    public ResponseEntity deleteAcademicWorkById(@RequestParam Long id) {
        academicWorkService.deleteAcademicWorkById(id);
        return new ResponseEntity("academic work are  deleted", HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity  updateAcademicWorkById(@RequestBody AcademicWork academicWork){
        academicWorkService.updateAcademicWork(academicWork);
        return new ResponseEntity("academic work are updated", HttpStatus.OK);
    }
}
