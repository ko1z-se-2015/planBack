package com.example.volunteer.controllers;

import com.example.volunteer.entities.Department;
import com.example.volunteer.entities.User;
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

    @GetMapping("/get-by-director")
    public ResponseEntity getDepartmentByDirector(@RequestHeader(value = "Authorization") String authorization){
        Department department = departmentService.getDepartmentByDirector(userService.getByToken(authorization));
        return ResponseEntity.ok(department);
    }

    @PostMapping("/transfer-teacher")
    public ResponseEntity transferTeacher(@RequestHeader(value = "Authorization") String authorization, @RequestBody Department d){
        Department department = departmentService.getDepartmentByTeacher(userService.getByToken(authorization));
        User user = userService.getByToken(authorization);
        departmentService.removeTeacher(department, user);
        departmentService.addTeacher(d, user);
        return ResponseEntity.ok("teacher transferred");
    }
}
