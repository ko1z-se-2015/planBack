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
    @ManyToOne
    private User createdBy;

}
