package com.example.volunteer.services;

import com.example.volunteer.entities.Notification;
import com.example.volunteer.entities.Plan;
import com.example.volunteer.entities.User;
import com.example.volunteer.repositories.NotificationRepo;
import com.example.volunteer.repositories.PlanRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NotificationService {

    private final NotificationRepo notificationRepo;
    private final EmailNotificationService emailNotificationService;
    private final PlanService planService;

    public List<Notification> getNotificationsBySendTo(User sendTo) {
        return notificationRepo.getNotificationsBySendTo(sendTo);
    }

    public List<Notification> getNotificationsBySendToAndStatus(User sendTo, String status) {
        return notificationRepo.getNotificationsBySendToAndStatus(sendTo, status);
    }

    public List<Notification> getNotificationsBySender(User sendBy) {
        return notificationRepo.getNotificationsBySendBy(sendBy);
    }

    public void createNotification(User sendBy, Long planId, boolean byTeacher, Notification notification) {
        Plan plan = planService.getPlanById(planId);
        User sendTo;

        if (byTeacher){
            sendTo = plan.getCreatedFor();
        } else {
            sendTo = plan.getCreatedBy();
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        plan.setStatus(notification.getStatus());

        notification.setSendBy(sendBy);
        notification.setSendTo(sendTo);
        notification.setPlanName(plan.getYear());
        notification.setSendDate(formattedDateTime);

        log.info(notification.toString());

        notificationRepo.save(notification);

        String subject = "";
        String textMessage = "";

        switch (notification.getStatus()){
            case "AWAITING":
                textMessage = String.format("The plan of %s academic year has been sent by %s %s for approval",
                        notification.getPlanName(), sendBy.getFirstName(), sendBy.getLastName());
                subject = String.format("%s %s's individual plan is %s",
                        sendBy.getFirstName(), sendBy.getLastName(), notification.getStatus());
                break;
            case "DENIED":
                textMessage = String.format(
                        "The plan of %s academic year has been returned for revision by %s %s" +
                                "\nParts to improve:" +
                                "\n%s" +
                                "\nComment:" +
                                "\n%s",
                        notification.getPlanName(), sendBy.getFirstName(), sendBy.getLastName(),
                        notification.getParts(), notification.getDescription());
                subject = String.format("Your individual plan is %s", notification.getStatus());
                break;
            case "APPROVED":
                textMessage = String.format("The plan of %s academic year has been approved by %s %s",
                        notification.getPlanName(), sendBy.getFirstName(), sendBy.getLastName());
                subject = String.format("Your individual plan is %s", notification.getStatus());

                plan.getAcademicWorks().forEach(academicWork -> academicWork.setId(null));
                plan.getAcademicMethods().forEach(academicMethod -> academicMethod.setId(null));
                plan.getResearchWorks().forEach(researchWork -> researchWork.setId(null));
                plan.getEducationalWorks().forEach(educationalWork -> educationalWork.setId(null));
                plan.getSocialWorks().forEach(socialWork -> socialWork.setId(null));
                plan.getKpis().forEach(kpi -> kpi.setId(null));

                Plan newPlan = new Plan(plan.getYear(), plan.getAcademicWorks(),
                        plan.getAcademicMethods(), plan.getResearchWorks(),
                        plan.getEducationalWorks(), plan.getSocialWorks(),
                        plan.getKpis(), plan.getCreatedBy(), plan.getCreatedFor());

                newPlan.setReport(true);

                planService.createPlan(newPlan);
                break;
        }
        emailNotificationService.sendSimpleMessage(
                sendTo.getEmail(), subject, textMessage);
    }
}
