package com.example.volunteer.modules;

import com.example.volunteer.entities.AcademicWork;
import com.example.volunteer.entities.ResearchWork;
import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResearchWorksToDelete {

    private List<ResearchWork> items;
}
