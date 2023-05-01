package com.example.volunteer.modules;

import com.example.volunteer.entities.AcademicWork;
import lombok.*;

import javax.persistence.Entity;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AcademicWorksToDelete {

    private List<AcademicWork> items;
}
