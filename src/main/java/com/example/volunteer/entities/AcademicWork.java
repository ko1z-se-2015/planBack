package com.example.volunteer.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "academic_work")
public class AcademicWork {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nameOfDiscipline;
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

    public AcademicWork(String nameOfDiscipline, String course, String trimester, String groups, String lecturesPlan, String lecturesFact, String practicesPlan, String practicesFact, String hoursPlan, String hoursFact, String totalPlan, String totalFact) {
        this.nameOfDiscipline = nameOfDiscipline;
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
    }
}
