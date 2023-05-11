package com.example.volunteer.modules;

import lombok.*;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
public class AddReseachWork {
    private Long idPlan;
    private String nameOfTheWork;
    private String deadlines;
    private String infoImplementation;
    private String results;
    private String comments;
//    private int typeOfTheWork;
//    //type
//    private String text1;
//    private String text2;
//    private String text3;
//
//    //common
//    private String deadlines;
//    private String informationOnImplementation;
//    private String results;
//    private String comments;
//
//    public AddReseachWork(Long idPlan, int typeOfTheWork, String text1, String text2, String text3, String deadlines, String informationOnImplementation, String results, String comments) {
//        this.idPlan = idPlan;
//        this.typeOfTheWork = typeOfTheWork;
//        this.text1 = text1;
//        this.text2 = text2;
//        this.text3 = text3;
//        this.deadlines = deadlines;
//        this.informationOnImplementation = informationOnImplementation;
//        this.results = results;
//        this.comments = comments;
//    }
//
//    public AddReseachWork(Long idPlan, int typeOfTheWork, String text1, String deadlines, String informationOnImplementation, String results, String comments) {
//        this.idPlan = idPlan;
//        this.typeOfTheWork = typeOfTheWork;
//        this.text1 = text1;
//        this.deadlines = deadlines;
//        this.informationOnImplementation = informationOnImplementation;
//        this.results = results;
//        this.comments = comments;
//    }
}
