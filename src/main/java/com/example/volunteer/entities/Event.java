package com.example.volunteer.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "это поле должно быть заполнена")
    @NotEmpty(message = "это поле должно быть заполнена")
    private String name;

    @NotNull(message = "это поле должно быть заполнена")
    @NotEmpty(message = "это поле должно быть заполнена")
    private String city;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern ="dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    @NotNull(message = "это поле должно быть заполнена")
    @Future(message = "Дата не должна быть прошлой")
    private LocalDate date;

    @NotNull(message = "это поле должно быть заполнена")
    @NotEmpty(message = "это поле должно быть заполнена")
    @Pattern(regexp = "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]-([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", message = "формат должен быть XX:XX-XX:XX")
    private String time;

    @NotNull(message = "это поле должно быть заполнена")
    private Integer amountOfVolunteer;

    @NotNull(message = "это поле должно быть заполнена")
    @NotEmpty(message = "это поле должно быть заполнена")
    private String description;

    @Type(type = "text")
    @NotNull(message = "выберите фотографию")
    private String image;

    private boolean isFinished;

    @OneToOne
    private User organizer;

    @ManyToMany
    private List<User> participants;

    @NotNull(message = "Выберите место на карте")
    private double lat;

    private double lng;

}
