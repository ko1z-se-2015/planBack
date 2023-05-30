package com.example.volunteer.services;

import com.example.volunteer.entities.*;
import com.example.volunteer.repositories.NotificationRepo;
import com.example.volunteer.repositories.PlanRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NotificationService {

    private final NotificationRepo notificationRepo;
    private final EmailNotificationService emailNotificationService;
    private final PlanService planService;
    private final AcademicWorkService academicWorkService;
    private final AcademicMethodService academicMethodService;
    private final ResearchWorkService researchWorkService;
    private final EducationalWorkService educationalWorkService;
    private final SocialWorkService socialWorkService;
    private final KpiService kpiService;

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

                List<AcademicWork> academicWorks = new ArrayList<>();
                List<AcademicMethod> academicMethods = new ArrayList<>();
                List<ResearchWork> researchWorks = new ArrayList<>();
                List<EducationalWork> educationalWorks = new ArrayList<>();
                List<SocialWork> socialWorks = new ArrayList<>();
                List<KPI> kpis = new ArrayList<>();

                for (AcademicWork academicWork : plan.getAcademicWorks()) {
                    AcademicWork newAcademicWork = new AcademicWork(academicWork.getNameOfDiscipline(), academicWork.getCourse(),
                            academicWork.getTrimester(), academicWork.getGroups(),
                            academicWork.getLecturesPlan(), academicWork.getLecturesFact(),
                            academicWork.getPracticesPlan(), academicWork.getPracticesFact(),
                            academicWork.getHoursPlan(), academicWork.getHoursFact(),
                            academicWork.getTotalPlan(), academicWork.getTotalFact());
                    academicWorkService.saveAcademicWork(newAcademicWork);
                    academicWorks.add(newAcademicWork);
                }

                for (AcademicMethod academicMethod : plan.getAcademicMethods()) {
                    AcademicMethod newAcademicMethod = new AcademicMethod(academicMethod.getDiscipline(), academicMethod.getNameWork(),
                            academicMethod.getDeadlines(), academicMethod.getInfoImplementation(), academicMethod.getComment());
                    academicMethodService.saveAcademicMethod(newAcademicMethod);
                    academicMethods.add(newAcademicMethod);
                }

                for (ResearchWork researchWork : plan.getResearchWorks()) {
                    ResearchWork newResearchWork = new ResearchWork(researchWork.getNameOfTheWork(), researchWork.getDeadlines(),
                            researchWork.getInfoImplementation(), researchWork.getResults(), researchWork.getComments());
                    researchWorkService.saveResearchWork(newResearchWork);
                    researchWorks.add(newResearchWork);
                }

                for (EducationalWork educationalWork : plan.getEducationalWorks()) {
                    EducationalWork newEducationalWork = new EducationalWork(educationalWork.getNameOfTheWork(), educationalWork.getDeadlines(),
                            educationalWork.getInfoImplementation(), educationalWork.getResults(), educationalWork.getComments());
                    educationalWorkService.saveEducationalWork(newEducationalWork);
                    educationalWorks.add(newEducationalWork);
                }

                for (SocialWork socialWork : plan.getSocialWorks()) {
                    SocialWork newSocialWork = new SocialWork(socialWork.getNameOfTheWork(), socialWork.getDeadlines(),
                            socialWork.getInfoImplementation(), socialWork.getResults(), socialWork.getComments());
                    socialWorkService.saveSocialWork(newSocialWork);
                    socialWorks.add(newSocialWork);
                }

                for (KPI kpi : plan.getKpis()) {
                    KPI newKPI = new KPI(kpi.getNameOfTheWork(), kpi.getDeadlines(),
                            kpi.getInformationOnImplementation(), kpi.getResults(), kpi.getComments(),
                            kpi.getPdfFile(), kpi.getPdfFileName(), kpi.getPercentage(),
                            kpi.getAuthorsNumber(), kpi.getAnotherSectionNumber());
                    kpiService.saveKpi(kpi.getKpiSection().getId(), newKPI);
                    kpis.add(newKPI);
                }

                Plan newPlan = new Plan();
                newPlan.setYear(plan.getYear());
                newPlan.setCreatedBy(plan.getCreatedBy());
                newPlan.setCreatedFor(plan.getCreatedFor());
                newPlan.setAcademicWorks(academicWorks);
                newPlan.setAcademicMethods(academicMethods);
                newPlan.setResearchWorks(researchWorks);
                newPlan.setEducationalWorks(educationalWorks);
                newPlan.setSocialWorks(socialWorks);
                newPlan.setKpis(kpis);
                newPlan.setReport(true);

                planService.createPlan(newPlan);
                break;


        }
        emailNotificationService.sendSimpleMessage(
                sendTo.getEmail(), subject, textMessage);
    }
}
