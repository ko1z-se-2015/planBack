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
//    private int typeOfTheWork;
//    //type1
//    private String nameOfTheArticle;
//    private String nameOfTheJournal;
//    private String coAuthors;
//    //type2
//    private String conferenceName;
//    private String date;
//    private String topicOfTheSpeech;
//    //type3
//    private String event;
//    //private String nameOfTheArticle;
//    private String students;
//    //type4
//    private String nameOfTheWorks;
//    //common
//    private String deadlines;
//    private String informationOnImplementation;
//    private String results;
//    private String comments;

    private String nameOfTheWork;
    private String deadlines;
    private String infoImplementation;
    private String results;
    private String comments;

    public ResearchWork(String nameOfTheWork, String deadlines, String informationOnImplementation, String results, String comments) {
        this.nameOfTheWork = nameOfTheWork;
        this.deadlines = deadlines;
        this.infoImplementation = informationOnImplementation;
        this.results = results;
        this.comments = comments;
    }

    //type1
//    public ResearchWork(int typeOfTheWork, String text1, String text2, String text3, String deadlines, String informationOnImplementation, String results, String comments) {
//        this.typeOfTheWork = typeOfTheWork;
//        if (typeOfTheWork == 1) {
//            this.nameOfTheArticle = text1;
//            this.nameOfTheJournal = text2;
//            this.coAuthors = text3;
//        }
//        if (typeOfTheWork == 2) {
//            this.conferenceName = text1;
//            this.date = text2;
//            this.topicOfTheSpeech = text3;
//        }
//        if (typeOfTheWork == 3) {
//            this.nameOfTheArticle = text1;
//            this.event = text2;
//            this.students = text3;
//        }
//        this.deadlines = deadlines;
//        this.informationOnImplementation = informationOnImplementation;
//        this.results = results;
//        this.comments = comments;
//    }
//
//    public ResearchWork(int typeOfTheWork, String nameOfTheWorks, String deadlines, String informationOnImplementation, String results, String comments) {
//        this.typeOfTheWork = typeOfTheWork;
//        this.nameOfTheWorks = nameOfTheWorks;
//        this.deadlines = deadlines;
//        this.informationOnImplementation = informationOnImplementation;
//        this.results = results;
//        this.comments = comments;
//    }


}
