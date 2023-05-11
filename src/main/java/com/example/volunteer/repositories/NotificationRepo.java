package com.example.volunteer.repositories;


import com.example.volunteer.entities.Notification;
import com.example.volunteer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification,Long> {
    List<Notification> getNotificationsBySendTo(User sendTo);
    List<Notification> getNotificationsBySendToAndStatus(User sendTo, String status);
    List<Notification> getNotificationsBySendBy(User sendBy);
}
