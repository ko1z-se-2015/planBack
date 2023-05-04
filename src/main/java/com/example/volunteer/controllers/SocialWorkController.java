package com.example.volunteer.controllers;

import com.example.volunteer.entities.SocialWork;
import com.example.volunteer.modules.SocialWorksToDelete;
import com.example.volunteer.services.SocialWorkService;
import com.example.volunteer.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/social-work")
public class SocialWorkController {

    private final SocialWorkService socialWorkService;

    private final UserService userService;

    public SocialWorkController(SocialWorkService socialWorkService, UserService userService) {
        this.socialWorkService = socialWorkService;
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity saveMyAcademicWork(@RequestHeader(value = "Authorization") String token, @RequestBody SocialWork academicWork) {
        socialWorkService.saveSocialWork(academicWork);
        return new ResponseEntity("social work added", HttpStatus.CREATED);
    }

    @PostMapping("/delete-by-id")
    public ResponseEntity deleteAcademicWorkById(@RequestParam Long id) {
        socialWorkService.deleteSocialWorkById(id);
        return new ResponseEntity("social work is deleted", HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity deleteAcademicWorks(@RequestBody SocialWorksToDelete socialWork) {
        for(SocialWork item: socialWork.getItems()){
            socialWorkService.deleteSocialWorkById(item.getId());
        }
        return new ResponseEntity("social works are deleted", HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity  updateAcademicWorkById(@RequestBody SocialWork socialWork){
        socialWorkService.updateSocialWork(socialWork);
        return new ResponseEntity("social work is updated", HttpStatus.OK);
    }
}
