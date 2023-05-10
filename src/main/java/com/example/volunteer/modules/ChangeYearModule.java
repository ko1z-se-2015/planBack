package com.example.volunteer.modules;

import com.example.volunteer.entities.Plan;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChangeYearModule {
    private Plan plan;
    private String year;
}
