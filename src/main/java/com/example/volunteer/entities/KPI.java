package com.example.volunteer.entities;

import com.example.volunteer.entities.kpi_sections.KpiSection;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "kpi")

public class KPI {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 2000)
    private String nameOfTheWork;

    private String deadlines;

    private String informationOnImplementation;

    @Column(length = 2000)
    private String results;

    @Column(length = 2000)
    private String comments;

    @Column(columnDefinition = "TEXT")
    private String pdfFile;

    private String pdfFileName;

    private float percentage;

    private int authorsNumber; //TODO удалить, если хранить во фронте выгоднее

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "kpi_section_id")
    private KpiSection kpiSection;

    private int anotherSectionNumber;
    //TODO REDO KPI IF NECESSARY
    public KPI(String nameOfTheWork, String deadlines, String informationOnImplementation, String results, String comments, String pdfFile, String pdfFileName, float percentage, int authorsNumber, int anotherSectionNumber) {
        this.nameOfTheWork = nameOfTheWork;
        this.deadlines = deadlines;
        this.informationOnImplementation = informationOnImplementation;
        this.results = results;
        this.comments = comments;
        this.pdfFile = pdfFile;
        this.percentage = percentage;
        this.authorsNumber = authorsNumber;
        this.pdfFileName = pdfFileName;
        this.anotherSectionNumber = anotherSectionNumber;
    }

    public KPI(String nameOfTheWork, String deadlines, String informationOnImplementation, String results, String comments, String pdfFile, String pdfFileName, float percentage, int authorsNumber) {
        this.nameOfTheWork = nameOfTheWork;
        this.deadlines = deadlines;
        this.informationOnImplementation = informationOnImplementation;
        this.results = results;
        this.comments = comments;
        this.pdfFile = pdfFile;
        this.percentage = percentage;
        this.authorsNumber = authorsNumber;
        this.pdfFileName = pdfFileName;
    }

    public KPI(String nameOfTheWork, String deadlines, String informationOnImplementation, String results, String comments, String pdfFile, String pdfFileName, float percentage, int authorsNumber, KpiSection kpiSection, int anotherSectionNumber) {
        this.nameOfTheWork = nameOfTheWork;
        this.deadlines = deadlines;
        this.informationOnImplementation = informationOnImplementation;
        this.results = results;
        this.comments = comments;
        this.pdfFile = pdfFile;
        this.pdfFileName = pdfFileName;
        this.percentage = percentage;
        this.authorsNumber = authorsNumber;
        this.kpiSection = kpiSection;
        this.anotherSectionNumber = anotherSectionNumber;
    }
}
