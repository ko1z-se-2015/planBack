package com.example.volunteer.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "academic_method")
public class AcademicMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String discipline;
    private String nameWork;
    private String deadlines;
    private String infoImplementation;
    private String comment;
    @ManyToOne
    private User createdBy;
}
