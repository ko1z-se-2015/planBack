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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message = "'это поле должно быть заполнена")
    @NotEmpty(message = "это поле должно быть заполнена")
    private int type;

    @NotNull(message = "это поле должно быть заполнена")
    @NotEmpty(message = "это поле должно быть заполнена")
    private String description;


    @OneToOne
    private Teacher sendByTeacher;
    @OneToOne
    private Teacher sendByDirector;

    @OneToOne
    private Teacher sendToTeacher;
    @OneToOne
    private Teacher sendToDirector;

    @NotNull(message = "это поле должно быть заполнена")
    @NotEmpty(message = "это поле должно быть заполнена")
    private String sendFor;

    @NotNull(message = "это поле должно быть заполнена")
    @NotEmpty(message = "это поле должно быть заполнена")
    @Pattern(regexp = "^[0-9]{9,15}$" , message = "Введеный формат должен быть xxxxxxxxxx")
    private String phoneNumber;

    private int sendDate;




}
