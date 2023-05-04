package com.example.volunteer.modules;

import com.example.volunteer.entities.EducationalWork;
import com.example.volunteer.entities.ResearchWork;
import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EducationalWorksToDelete {

    private List<EducationalWork> items;
}
