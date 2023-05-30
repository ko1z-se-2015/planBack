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
import java.util.List;

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
    private String year;
    private String status; // SENT / CANCELED / APPROVED
    @OneToMany
    private List<AcademicWork> academicWorks;
    @OneToMany
    private List<AcademicMethod> academicMethods;
    @OneToMany
    private List<ResearchWork> researchWorks;
    @OneToMany
    private List<EducationalWork> educationalWorks;
    @OneToMany
    private List<SocialWork> socialWorks;
    @OneToMany
    private List<KPI> kpis;
    @OneToOne
    private User createdBy;
    @OneToOne
    private User createdFor;

    private boolean isReport = false;

    public Plan(Long id) {
        this.id = id;
    }

    public Plan(String year, List<AcademicWork> academicWorks, List<AcademicMethod> academicMethods, List<ResearchWork> researchWorks, List<EducationalWork> educationalWorks, List<SocialWork> socialWorks, List<KPI> kpis, User createdBy, User createdFor) {
        this.year = year;
        this.academicWorks = academicWorks;
        this.academicMethods = academicMethods;
        this.researchWorks = researchWorks;
        this.educationalWorks = educationalWorks;
        this.socialWorks = socialWorks;
        this.kpis = kpis;
        this.createdBy = createdBy;
        this.createdFor = createdFor;
    }
}
