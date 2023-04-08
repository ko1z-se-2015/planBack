package com.example.volunteer.services;

import com.example.volunteer.entities.Notification;
import com.example.volunteer.entities.User;
import com.example.volunteer.repositories.NotificationRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NotificationService {

    private final NotificationRepo notificationRepo;

    public void createNotification(Notification notification){
        notificationRepo.save(notification);
    }
    public List<Notification> getNotificationsBySender(User user){
        return notificationRepo.findAllBySendBy(user);
    }

    public List<Notification> getNotificationsBySendTo(User user){
        return notificationRepo.findAllBySendTo(user);
    }
}
