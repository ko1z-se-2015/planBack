package com.example.volunteer.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "'это поле должно быть заполнена")
    @NotEmpty(message = "это поле должно быть заполнена")
    private String firstName;

    @NotNull(message = "это поле должно быть заполнена")
    @NotEmpty(message = "это поле должно быть заполнена")
    private String lastName;

    @NotNull(message = "это поле должно быть заполнена")
    @NotEmpty(message = "это поле должно быть заполнена")
    private String middleName;

    @Column(columnDefinition = "TEXT")
    private String photo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "degree_id")
    private Degree degree;

    private String rate;

    @NotNull(message = "это поле должно быть заполнена")
    @NotEmpty(message = "это поле должно быть заполнена")
    @Email(message = "формат почты неправильный")
    private String email;

    @NotNull(message = "это поле должно быть заполнена")
    @NotEmpty(message = "это поле должно быть заполнена")
    private String password;

//    @NotNull(message = "это поле должно быть заполнена")
//    @NotEmpty(message = "это поле должно быть заполнена")
//    @Pattern(regexp = "^[0-9]{9,15}$" , message = "Введеный формат должен быть xxxxxxxxxx")
//    private String phoneNumber;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<Role> roles = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "sendTo", cascade = CascadeType.ALL)
    private List<Notification> notifications = new ArrayList<>();

    public User(String firstName, String lastName, String middleName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.password = password;
    }

    public User(String firstName, String lastName, String middleName, String rate, String email, String password) {
    this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.rate = rate;
        this.email = email;
        this.password = password;
    }

    public User(String firstName, String lastName, String middleName, String photo, String rate, Position position, Degree degree) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.photo = photo;
        this.rate = rate;
        this.position = position;
        this.degree = degree;

    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
