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

    @JsonIgnore
    @OneToOne(mappedBy = "kpi", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PdfFile pdfFile;

    private float percentage;

    private int authorsNumber; //TODO удалить, если хранить во фронте выгоднее

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "kpi_section_id")
    private KpiSection kpiSection;


    //TODO REDO KPI IF NECESSARY
    public KPI(String nameOfTheWork, String deadlines, String informationOnImplementation, String results, String comments, PdfFile pdfFile, float percentage, int authorsNumber, KpiSection kpiSection) {
        this.nameOfTheWork = nameOfTheWork;
        this.deadlines = deadlines;
        this.informationOnImplementation = informationOnImplementation;
        this.results = results;
        this.comments = comments;
        this.pdfFile = pdfFile;
        this.percentage = percentage;
        this.authorsNumber = authorsNumber;
        this.kpiSection = kpiSection;
    }

}
