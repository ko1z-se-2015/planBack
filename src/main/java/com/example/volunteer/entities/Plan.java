package com.example.volunteer.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "plans")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String year;
    @OneToMany
    private List<AcademicWork> academicWorks;
    @OneToMany
    private List<AcademicMethod> academicMethods;
    @OneToOne
    private User createdBy;
    @OneToOne
    private User createdFor;

    public Plan(Long id) {
        this.id = id;
    }
}
