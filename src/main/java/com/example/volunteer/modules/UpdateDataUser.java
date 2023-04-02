package com.example.volunteer.modules;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UpdateDataUser {



    @NotNull(message = "'это поле должно быть заполнена")
    @NotEmpty(message = "это поле должно быть заполнена")
    private String firstName;

    @NotNull(message = "'это поле должно быть заполнена")
    @NotEmpty(message = "это поле должно быть заполнена")
    private String secondName;
    @NotNull(message = "'это поле должно быть заполнена")
    @NotEmpty(message = "это поле должно быть заполнена")
    @Pattern(regexp = "^[0-9]{9,15}$" , message = "Введеный формат должен быть xxxxxxxxxx")
    private String phoneNumber;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern ="dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    @NotNull(message = "это поле должно быть заполнена")
    @Past(message = "Дата должна быть прошлой")
    private LocalDate dateOfBirthday;


    private String organizationName;
}
