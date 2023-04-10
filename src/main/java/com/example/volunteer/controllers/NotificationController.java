package com.example.volunteer.controllers;

import com.example.volunteer.entities.Notification;
import com.example.volunteer.entities.User;
import com.example.volunteer.services.NotificationService;
import com.example.volunteer.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;

    public NotificationController(NotificationService notificationService, UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @PostMapping("/send")
    public ResponseEntity sendNotification(@RequestBody Notification Notification) {
        notificationService.createNotification(Notification);
        return new ResponseEntity("notification was added", HttpStatus.CREATED);

    }
    @GetMapping("/get-by-me")
    public ResponseEntity getSendBeMeNotification(@RequestHeader(value="Authorization") String authorization){
        User user = userService.getByToken(authorization);
        return ResponseEntity.ok(notificationService.getNotificationsBySender(user));
    }

    @GetMapping("/get-to-me")
    public ResponseEntity getSendToMeNotification(@RequestHeader(value="Authorization") String authorization){
        User user = userService.getByToken(authorization);
        return ResponseEntity.ok(notificationService.getNotificationsBySendTo(user));
    }

}
