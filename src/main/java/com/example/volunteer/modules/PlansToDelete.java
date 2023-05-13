package com.example.volunteer.modules;

import com.example.volunteer.entities.Plan;
import com.example.volunteer.entities.ResearchWork;
import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlansToDelete {

    private List<Plan> items;
}
