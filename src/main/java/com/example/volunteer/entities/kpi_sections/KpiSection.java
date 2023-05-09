package com.example.volunteer.entities.kpi_sections;

import com.example.volunteer.entities.Degree;
import com.example.volunteer.entities.KPI;
import com.example.volunteer.entities.Position;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "kpi_section")
public class KpiSection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private int sectionNumber;

    @Column(length = 2000)
    private String name;

    @Column(length = 2000)
    private String notes;

    @ElementCollection
    @Column(length = 2000)
    private List<String> options;

    @Column
    private float rate_full;

    @Column
    private float rate_half;

    @Column
    private float rate_quarter;

    @Column
    private int percentage;

    @Column
    private boolean authorsByParts;

    @Column
    private boolean anotherSection; //SHOULD BE TRUE IF APPLICABLE (IF THERE IS A SOLUTION OF CLOSING THE ANOTHER SECTION BY THIS SECTION), OTHERWISE SHOULD BE FALSE AND HIDDEN FOR THE USER

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "degree_id")
    private Degree degree;

    @JsonIgnore
    @OneToMany(mappedBy = "kpiSection", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<KPI> kpiList = new ArrayList<>();

    public KpiSection(int sectionNumber, String name, String notes, List<String> options, float rate_full, float rate_half, float rate_quarter, int percentage, boolean authorsByParts, boolean anotherSection) {
        this.sectionNumber = sectionNumber;
        this.name = name;
        this.notes = notes;
        this.options = options;
        this.rate_full = rate_full;
        this.rate_half = rate_half;
        this.rate_quarter = rate_quarter;
        this.percentage = percentage;
        this.authorsByParts = authorsByParts;
        this.anotherSection = anotherSection;
    }
}
