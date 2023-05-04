package com.example.volunteer.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "educational_work")
public class EducationalWork {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nameOfTheWork;
    private String deadlines;
    private String infoImplementation;
    private String results;
    private String comments;

    public EducationalWork(String nameOfTheWork, String deadlines, String informationOnImplementation, String results, String comments) {
        this.nameOfTheWork = nameOfTheWork;
        this.deadlines = deadlines;
        this.infoImplementation = informationOnImplementation;
        this.results = results;
        this.comments = comments;
    }
}
