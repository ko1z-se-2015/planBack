package com.example.volunteer.controllers;

import com.example.volunteer.entities.ResearchWork;
import com.example.volunteer.modules.ResearchWorksToDelete;
import com.example.volunteer.services.ResearchWorkService;
import com.example.volunteer.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/research-work")
public class ResearchWorkController {

    private final ResearchWorkService researchWorkService;

    private final UserService userService;

    public ResearchWorkController(ResearchWorkService researchWorkService, UserService userService) {
        this.researchWorkService = researchWorkService;
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity saveMyResearchWork(@RequestHeader(value = "Authorization") String token, @RequestBody ResearchWork researchWork) {
        researchWorkService.saveResearchWork(researchWork);
        return new ResponseEntity("research work added", HttpStatus.CREATED);
    }

    @PostMapping("/delete-by-id")
    public ResponseEntity deleteResearchWorkById(@RequestParam Long id) {
        researchWorkService.deleteResearchWorkById(id);
        return new ResponseEntity("research work are  deleted", HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity deleteResearchWorks(@RequestBody ResearchWorksToDelete researchWorks) {
        for(ResearchWork item: researchWorks.getItems()){
            researchWorkService.deleteResearchWorkById(item.getId());
        }
        return new ResponseEntity("research works are deleted", HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity  updateResearchWorkById(@RequestBody ResearchWork researchWork){
        researchWorkService.updateResearchWork(researchWork);
        return new ResponseEntity("research work are updated", HttpStatus.OK);
    }
}
