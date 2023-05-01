package com.example.volunteer.modules;

import com.example.volunteer.entities.AcademicMethod;
import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AcademicMethodsToDelete {

    private List<AcademicMethod> items;
}
