package com.example.volunteer.controllers;

import com.example.volunteer.entities.Department;
import com.example.volunteer.services.DepartmentService;
import com.example.volunteer.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;
    private final UserService userService;

    public DepartmentController(DepartmentService departmentService, UserService userService) {
        this.departmentService = departmentService;
        this.userService = userService;
    }

    @GetMapping("/get-all")
    public ResponseEntity getAll(){
        List<Department> departments = departmentService.getDepartments();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/getByTeacher")
    public ResponseEntity getDepartmentByTeacher(@RequestHeader(value = "Authorization") String authorization){
        Department department = departmentService.getDepartmentByTeacher(userService.getByToken(authorization));
        return ResponseEntity.ok(department);
    }
}
