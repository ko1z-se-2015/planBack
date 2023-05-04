package com.example.volunteer.modules;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddEducationalWork {
    private Long idPlan;
    private String nameOfTheWork;
    private String deadlines;
    private String informationOnImplementation;
    private String results;
    private String comments;
}
