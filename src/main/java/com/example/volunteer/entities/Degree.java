package com.example.volunteer.entities;

import com.example.volunteer.entities.kpi_sections.KpiSection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "degree")
public class Degree {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String nameRu;

    @Column(columnDefinition = "TEXT")
    private String nameKz;

    @Column(columnDefinition = "TEXT")
    private String nameEn;

    @OneToMany(mappedBy = "degree", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "degree", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<KpiSection> kpiSections = new ArrayList<>();

    public Degree(String nameRu, String nameKz, String nameEn) {
        this.nameRu = nameRu;
        this.nameKz = nameKz;
        this.nameEn = nameEn;
    }
}
