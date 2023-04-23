package com.example.volunteer.repositories;

import com.example.volunteer.entities.Department;
import com.example.volunteer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepo extends JpaRepository<Department,Long> {
    Department findDepartmentByDirector(User user);
    Department findDepartmentByTeachersIn(List<User> user);
    Department findByName(String name);
}
