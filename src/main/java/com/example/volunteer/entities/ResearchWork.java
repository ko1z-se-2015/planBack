package com.example.volunteer.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "research_work")
public class ResearchWork {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String typeOfTheWork;
    //type1
    private String nameOfTheArticle;
    private String nameOfTheJournal;
    private String coAuthors;
    //type2
    private String conferenceName;
    private Date date;
    private String topicOfTheSpeech;
    //type3
    private String event;
    //private String nameOfTheArticle;
    private String students;
    //type4
    private String nameOfTheWorks;
    //common
    private String deadlines;
    private String informationOnImplementation;
    private String results;
    private String comments;

    //type1
    public ResearchWork(String typeOfTheWork, String nameOfTheArticle, String nameOfTheJournal, String coAuthors, String deadlines, String informationOnImplementation, String results, String comments) {
        this.typeOfTheWork = typeOfTheWork;
        this.nameOfTheArticle = nameOfTheArticle;
        this.nameOfTheJournal = nameOfTheJournal;
        this.coAuthors = coAuthors;
        this.deadlines = deadlines;
        this.informationOnImplementation = informationOnImplementation;
        this.results = results;
        this.comments = comments;
    }


}
