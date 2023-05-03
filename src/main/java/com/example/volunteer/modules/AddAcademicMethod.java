package com.example.volunteer.modules;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddAcademicMethod {
    private Long idPlan;
    private String discipline;
    private String nameWork;
    private String deadlines;
    private String infoImplementation;
    private String comment;
}
