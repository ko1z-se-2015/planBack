package com.example.volunteer;

import com.example.volunteer.entities.Department;
import com.example.volunteer.entities.Plan;
import com.example.volunteer.entities.User;
import com.example.volunteer.services.DepartmentService;
import com.example.volunteer.services.PlanService;
import com.example.volunteer.services.RoleService;
import com.example.volunteer.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class VolunteerApplication {

    public static void main(String[] args) {

        SpringApplication.run(VolunteerApplication.class, args);

    }

    User teacher1 = new User("Amira", "Balkiyaeva", "Talgatkyzy", "Senior-lector", "High-research", "0.5", "ako@mail.ru", "ako");
    //User teacher = new User("Kanat", "Berkinbayev", "Galymuly", "Associate Professor", "Research", "asd@asd.ru", "qwerty", "77777777777", "asdasd");
    User director = new User("Daniyar", "Myrzasary", "Timuruly", "Director of Department", "Research", "1", "kk@kk.kz", "qwerty");
    //Department department = new Department("IT department", director);
//    Plan plan = new Plan("as", "das", "das", "das", "das", "da", "ds", "aa", "aa", "aa", "aa", "aa", "aa", "aa", "aa", "aa", "aa", "aa", "aa", "aa", "aa", "aa", "aa");

    @Bean
    CommandLineRunner run(RoleService roleService, UserService userService, DepartmentService departmentService, PlanService planService) {
        return args -> {
            roleService.addRole("TEACHER");
            roleService.addRole("DIRECTOR");
            userService.createTeacher(teacher);
            userService.createDirector(director);
            departmentService.createDepartment(department);
            departmentService.addTeacher(department, teacher);
//            plan.setCreatedBy(userService.getByEmail("asd@asd.ru"));
//            plan.setCreatedFor(userService.getByEmail("kk@kk.kz"));
//            planService.createPlan(plan);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
