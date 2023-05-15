package com.example.volunteer.controllers;

import com.example.volunteer.entities.Notification;
import com.example.volunteer.entities.User;
import com.example.volunteer.services.EmailNotificationService;
import com.example.volunteer.services.NotificationService;
import com.example.volunteer.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity sendNotification(@RequestHeader(value="Authorization") String authorization,
                                           @RequestParam Long planId,
                                           @RequestParam(defaultValue = "true") boolean byTeacher,
                                           @RequestBody Notification notification) {
        User user = userService.getByToken(authorization);
        notificationService.createNotification(user, planId, byTeacher, notification);
        return new ResponseEntity("notification was added", HttpStatus.CREATED);
    }

    @GetMapping("/get-by-me")
    public ResponseEntity<List<Notification>> getSendBeMeNotification(@RequestHeader(value="Authorization") String authorization){
        User user = userService.getByToken(authorization);
        return ResponseEntity.ok(notificationService.getNotificationsBySender(user));
    }

    @GetMapping("/get-to-me")
    public ResponseEntity<List<Notification>> getSendToMeNotification(@RequestHeader(value="Authorization") String authorization){
        User user = userService.getByToken(authorization);
        return ResponseEntity.ok(notificationService.getNotificationsBySendTo(user));
    }

    @GetMapping("/get-to-me-by-status")
    public ResponseEntity<List<Notification>> getSendToMeNotificationByStatus(@RequestHeader(value="Authorization") String authorization,
                                                          @RequestParam String status){
        User user = userService.getByToken(authorization);
        return ResponseEntity.ok(notificationService.getNotificationsBySendToAndStatus(user, status));
    }

}
