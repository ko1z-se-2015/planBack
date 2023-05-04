package com.example.volunteer.modules;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddSocialWork {
    private Long idPlan;
    private String nameOfTheWork;
    private String deadlines;
    private String infoImplementation;
    private String results;
    private String comments;
}
