package com.example.volunteer.modules;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UpdateDataEvent {

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
    private String time;

    @NotNull(message = "это поле должно быть заполнена")
    private Integer amountOfVolunteer;

    @NotNull(message = "это поле должно быть заполнена")
    @NotEmpty(message = "это поле должно быть заполнена")
    private String description;


    private String image;


    private double lat ;

    private double lng ;
}
