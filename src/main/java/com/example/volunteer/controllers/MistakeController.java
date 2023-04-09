package com.example.volunteer.controllers;

import com.example.volunteer.entities.Mistakes;
import com.example.volunteer.services.MistakeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/mistake")
public class MistakeController {

    private final MistakeService mistakeService;

    public MistakeController(MistakeService mistakeService) {
        this.mistakeService = mistakeService;
    }

    @GetMapping("/get-mistakes")
    public ResponseEntity getMistakes(){
        List<Mistakes> mistakes = mistakeService.getMistakes();
        return ResponseEntity.ok(mistakes);
    }
}
