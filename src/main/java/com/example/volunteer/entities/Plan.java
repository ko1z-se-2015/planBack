package com.example.volunteer.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "plans")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nameDiscipline;
    private String course;
    private String trimester;
    private String groups;
    private String lecturesPlan;
    private String lecturesFact;
    private String practicesPlan;
    private String practicesFact;
    private String hoursPlan;
    private String hoursFact;
    private String totalPlan;
    private String totalFact;
    private String discipline;
    private String nameWork;
    private String deadlines;
    private String infoImplementation1;
    private String comment1;
    private String typeWork;
    private String journal;
    private String deadline;
    private String article;
    private String infoImplementation2;
    private String comment2;

    @OneToOne
    private User createdBy;
    @OneToOne
    private User createdFor;

    public Plan(String nameDiscipline, String course, String trimester, String groups, String lecturesPlan, String lecturesFact, String practicesPlan, String practicesFact, String hoursPlan, String hoursFact, String totalPlan, String totalFact, String discipline, String nameWork, String deadlines, String infoImplementation1, String comment1, String typeWork, String journal, String deadline, String article, String infoImplementation2, String comment2) {
        this.nameDiscipline = nameDiscipline;
        this.course = course;
        this.trimester = trimester;
        this.groups = groups;
        this.lecturesPlan = lecturesPlan;
        this.lecturesFact = lecturesFact;
        this.practicesPlan = practicesPlan;
        this.practicesFact = practicesFact;
        this.hoursPlan = hoursPlan;
        this.hoursFact = hoursFact;
        this.totalPlan = totalPlan;
        this.totalFact = totalFact;
        this.discipline = discipline;
        this.nameWork = nameWork;
        this.deadlines = deadlines;
        this.infoImplementation1 = infoImplementation1;
        this.comment1 = comment1;
        this.typeWork = typeWork;
        this.journal = journal;
        this.deadline = deadline;
        this.article = article;
        this.infoImplementation2 = infoImplementation2;
        this.comment2 = comment2;
    }

    public Plan(String nameDiscipline, String course, String trimester, String groups, String lecturesPlan, String lecturesFact, String practicesPlan, String practicesFact, String hoursPlan, String hoursFact, String totalPlan, String totalFact, String discipline, String nameWork, String deadlines, String infoImplementation1, String comment1, String typeWork, String journal, String deadline, String article, String infoImplementation2, String comment2, User createdBy, User createdFor) {
        this.nameDiscipline = nameDiscipline;
        this.course = course;
        this.trimester = trimester;
        this.groups = groups;
        this.lecturesPlan = lecturesPlan;
        this.lecturesFact = lecturesFact;
        this.practicesPlan = practicesPlan;
        this.practicesFact = practicesFact;
        this.hoursPlan = hoursPlan;
        this.hoursFact = hoursFact;
        this.totalPlan = totalPlan;
        this.totalFact = totalFact;
        this.discipline = discipline;
        this.nameWork = nameWork;
        this.deadlines = deadlines;
        this.infoImplementation1 = infoImplementation1;
        this.comment1 = comment1;
        this.typeWork = typeWork;
        this.journal = journal;
        this.deadline = deadline;
        this.article = article;
        this.infoImplementation2 = infoImplementation2;
        this.comment2 = comment2;
        this.createdBy = createdBy;
        this.createdFor = createdFor;
    }
}
