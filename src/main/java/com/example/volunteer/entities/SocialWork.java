package com.example.volunteer.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "social_work")
public class SocialWork {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nameOfTheWork;
    private String deadlines;
    private String infoImplementation;
    private String results;
    private String comments;

    public SocialWork(String nameOfTheWork, String deadlines, String infoImplementation, String results, String comments) {
        this.nameOfTheWork = nameOfTheWork;
        this.deadlines = deadlines;
        this.infoImplementation = infoImplementation;
        this.results = results;
        this.comments = comments;
    }
}
