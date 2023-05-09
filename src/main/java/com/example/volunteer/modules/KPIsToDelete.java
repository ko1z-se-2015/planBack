package com.example.volunteer.modules;

import com.example.volunteer.entities.KPI;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class KPIsToDelete {
    private List<KPI> items;
}
