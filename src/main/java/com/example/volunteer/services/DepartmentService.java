package com.example.volunteer.services;

import com.example.volunteer.entities.Department;
import com.example.volunteer.entities.User;
import com.example.volunteer.repositories.DepartmentRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DepartmentService {

    private final DepartmentRepo departmentRepo;

    public void createDepartment(Department department){
       departmentRepo.save(department);
    }

    public Department getDepartmentByDirector(User user){
        return departmentRepo.findDepartmentByDirector(user);
    }

        public Department getDepartmentByTeacher(User user){
        List<User> users = new ArrayList<>();
        users.add(user);
        return  departmentRepo.findDepartmentByTeachersIn(users);
    }

    public void addTeacher(Department department, User user) {
        Department d = departmentRepo.findByName(department.getName());
        d.getTeachers().add(user);
        departmentRepo.save(d);
    }

    public void removeTeacher(Department department, User user) {
        department.getTeachers().remove(user);
        departmentRepo.save(department);
    }

    public Department findByName(String name) {
        return departmentRepo.findByName(name);
    }

    public List<Department> getDepartments(){
        return departmentRepo.findAll();
    }


}
