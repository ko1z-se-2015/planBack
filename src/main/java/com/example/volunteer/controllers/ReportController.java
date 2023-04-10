package com.example.volunteer.controllers;

import com.example.volunteer.entities.Report;
import com.example.volunteer.entities.User;
import com.example.volunteer.services.ReportService;
import com.example.volunteer.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;
    private final UserService userService;

    public ReportController(ReportService reportService, UserService userService) {
        this.reportService = reportService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity createReport(@RequestHeader(value="Authorization") String authorization, Report report){
        User user = userService.getByToken(authorization);
        Report report1 = report;
        report1.setSendBy(user);
        reportService.createReport(report1);
        return new ResponseEntity("report created", HttpStatus.CREATED);
    }
}
