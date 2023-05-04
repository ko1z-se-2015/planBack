package com.example.volunteer.controllers;

import com.example.volunteer.entities.EducationalWork;
import com.example.volunteer.modules.EducationalWorksToDelete;
import com.example.volunteer.services.EducationalWorkService;
import com.example.volunteer.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/educational-work")
public class EducationanlWorkController {

    private final EducationalWorkService educationalWorkService;

    private final UserService userService;

    public EducationanlWorkController(EducationalWorkService educationalWorkService, UserService userService) {
        this.educationalWorkService = educationalWorkService;
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity saveMyAcademicWork(@RequestHeader(value = "Authorization") String token, @RequestBody EducationalWork academicWork) {
        educationalWorkService.saveEducationalWork(academicWork);
        return new ResponseEntity("educational work added", HttpStatus.CREATED);
    }

    @PostMapping("/delete-by-id")
    public ResponseEntity deleteAcademicWorkById(@RequestParam Long id) {
        educationalWorkService.deleteEducationalWorkById(id);
        return new ResponseEntity("educational work is deleted", HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity deleteAcademicWorks(@RequestBody EducationalWorksToDelete educationalWork) {
        for(EducationalWork item: educationalWork.getItems()){
            educationalWorkService.deleteEducationalWorkById(item.getId());
        }
        return new ResponseEntity("educational works are deleted", HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity  updateAcademicWorkById(@RequestBody EducationalWork educationalWork){
        educationalWorkService.updateEducationalWork(educationalWork);
        return new ResponseEntity("educational work is updated", HttpStatus.OK);
    }
}
