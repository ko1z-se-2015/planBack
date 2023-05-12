package com.example.volunteer.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String planName;

    private String status;

    @ElementCollection
    private List<String> parts; //PARTS THAT SHOULD BE IMPROVED

    private String description;

    @OneToOne
    private User sendBy;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "send_to_id")
    private User sendTo;

    private String sendDate;

    public Notification(String status, List<String> parts, String description) {
        this.status = status;
        this.parts = parts;
        this.description = description;
    }

    public Notification(String status) {
        this.status = status;
    }


}
