package com.example.volunteer;

import com.example.volunteer.entities.Department;
import com.example.volunteer.entities.User;
import com.example.volunteer.services.DepartmentService;
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
	User teacher = new User("Kanat", "Berkinbayev", "Galymuly", "Associate Professor", "Research", "asd@asd.ru", "qwerty", "77777777777", "asdasd");
	User director = new User("Daniyar", "Myrzasary", "Timuruly", "Director of Department", "Research", "kk@kk.kz", "qwerty", "77777777777", "asdasd");
	Department department = new Department("IT department", director);

	@Bean
	CommandLineRunner run(RoleService roleService, UserService userService, DepartmentService departmentService) {
		return args -> {
			roleService.addRole("TEACHER");
			roleService.addRole("DIRECTOR");
			userService.createTeacher(teacher);
			userService.createDirector(director);
			departmentService.createDepartment(department);
			departmentService.addTeacher(department, teacher);
		};
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
