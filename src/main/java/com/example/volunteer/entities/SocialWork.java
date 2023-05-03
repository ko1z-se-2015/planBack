package com.example.volunteer.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "social_work")
public class SocialWork {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nameOfWork;
    private String deadlines;
    private String informationOnImplementation;
    private String results;
    private String comments;
}
