package com.example.volunteer.modules;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateAcademicWork {

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
}
