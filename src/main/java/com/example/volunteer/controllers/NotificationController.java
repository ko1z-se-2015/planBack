package com.example.volunteer.controllers;

import com.example.volunteer.entities.Notification;
import com.example.volunteer.services.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public ResponseEntity sendNotification(@RequestBody Notification Notification) {
        notificationService.createNotification(Notification);
        return new ResponseEntity("notification was added", HttpStatus.CREATED);

    }

}
