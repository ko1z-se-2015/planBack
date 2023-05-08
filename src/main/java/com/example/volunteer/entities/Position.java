package com.example.volunteer.entities;

import com.example.volunteer.entities.kpi_sections.KpiSection;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "position")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String nameRu;

    @Column(columnDefinition = "TEXT")
    private String nameKz;

    @Column(columnDefinition = "TEXT")
    private String nameEn;

    @JsonIgnore
    @OneToMany(mappedBy = "position", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "position", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<KpiSection> kpiSections = new ArrayList<>();

    public Position(String nameRu, String nameKz, String nameEn) {
        this.nameRu = nameRu;
        this.nameKz = nameKz;
        this.nameEn = nameEn;
    }
}
