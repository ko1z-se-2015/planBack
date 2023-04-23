package com.example.volunteer;

import com.example.volunteer.entities.User;
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

	@Bean
	CommandLineRunner run(RoleService roleService, UserService userService) {
		return args -> {
			roleService.addRole("TEACHER");
			roleService.addRole("DIRECTOR");
			userService.createTeacher(new User("Kanat", "Berkinbayev", "Galymuly", "asda@asd.ru", "qwerty", "77777777777", "asdasd"));
		};
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
