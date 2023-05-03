package com.example.volunteer.controllers;

import com.example.volunteer.entities.AcademicMethod;
import com.example.volunteer.entities.User;
import com.example.volunteer.modules.AcademicMethodsToDelete;
import com.example.volunteer.modules.AcademicWorksToDelete;
import com.example.volunteer.modules.UpdateAcademicMethod;
import com.example.volunteer.modules.UpdateAcademicWork;
import com.example.volunteer.services.AcademicMethodService;
import com.example.volunteer.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/academic-method")
public class AcademicMethodController {

    private final AcademicMethodService academicMethodService;

    private final UserService userService;

    public AcademicMethodController(AcademicMethodService academicMethodService, UserService userService) {
        this.academicMethodService = academicMethodService;
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity saveMyAcademicWork(@RequestHeader(value = "Authorization") String token, @RequestBody AcademicMethod academicWork) {
        academicMethodService.saveAcademicMethod(academicWork);
        return new ResponseEntity("academic method added", HttpStatus.CREATED);
    }

    @PostMapping("/delete-by-id")
    public ResponseEntity deleteAcademicWorkById(@RequestParam Long id) {
        academicMethodService.deleteAcademicMethodById(id);
        return new ResponseEntity("academic method is deleted", HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity deleteAcademicWorks(@RequestBody AcademicMethodsToDelete academicWorks) {
        for(AcademicMethod item: academicWorks.getItems()){
            academicMethodService.deleteAcademicMethodById(item.getId());
        }
        return new ResponseEntity("academic methods are deleted", HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity  updateAcademicWorkById(@RequestBody UpdateAcademicMethod academicMethod){
        academicMethodService.updateAcademicMethod(academicMethod);
        return new ResponseEntity("academic method is updated", HttpStatus.OK);
    }
}
