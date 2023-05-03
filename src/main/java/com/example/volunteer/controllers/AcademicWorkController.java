package com.example.volunteer.controllers;

import com.example.volunteer.entities.AcademicWork;
import com.example.volunteer.entities.User;
import com.example.volunteer.modules.AcademicWorksToDelete;
import com.example.volunteer.modules.UpdateAcademicWork;
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
        academicWorkService.saveAcademicWork(academicWork);
        return new ResponseEntity("academic work added", HttpStatus.CREATED);
    }

    @PostMapping("/delete-by-id")
    public ResponseEntity deleteAcademicWorkById(@RequestParam Long id) {
        academicWorkService.deleteAcademicWorkById(id);
        return new ResponseEntity("academic work are  deleted", HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity deleteAcademicWorks(@RequestBody AcademicWorksToDelete academicWorks) {
        for(AcademicWork item: academicWorks.getItems()){
            academicWorkService.deleteAcademicWorkById(item.getId());
        }
        return new ResponseEntity("academic works are deleted", HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity  updateAcademicWorkById(@RequestBody UpdateAcademicWork academicWork){
        academicWorkService.updateAcademicWork(academicWork);
        return new ResponseEntity("academic work are updated", HttpStatus.OK);
    }
}
