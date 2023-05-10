package com.example.volunteer.controllers;


import com.auth0.jwt.JWT;

import com.auth0.jwt.algorithms.Algorithm;

import com.example.volunteer.entities.Degree;
import com.example.volunteer.entities.Position;
import com.example.volunteer.entities.User;
import com.example.volunteer.services.DegreeService;
import com.example.volunteer.services.PositionService;
import com.example.volunteer.services.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

//
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final PositionService positionService;
    private final DegreeService degreeService;

    public UserController(UserService userService, PositionService positionService, DegreeService degreeService) {
        this.userService = userService;
        this.positionService = positionService;
        this.degreeService = degreeService;
    }

    @PostMapping("/createTeacher")
    public ResponseEntity createTeacher(@RequestBody User user) {
        if (userService.createTeacher(user)) {
            return new ResponseEntity("user was added", HttpStatus.CREATED);
        }

        return new ResponseEntity("user wasn't added", HttpStatus.BAD_REQUEST);
    }

    //
    @PostMapping("/createDirector")
    public ResponseEntity createDirector(@RequestBody User user) {
        if (userService.createTeacher(user)) {
            return new ResponseEntity("user was added", HttpStatus.CREATED);
        }

        return new ResponseEntity("user wasn't added", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/update")
    public ResponseEntity updateUser(@RequestHeader(value = "Authorization") String authorization, @RequestBody User updatedUser) {
        User user = userService.getByToken(authorization);
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setMiddleName(updatedUser.getMiddleName());
        user.setPhoto(updatedUser.getPhoto());
        user.setRate(updatedUser.getRate());
        user.setPosition(updatedUser.getPosition());
        user.setDegree(updatedUser.getDegree());
        userService.update(user);
        return ResponseEntity.ok("user updated");
    }

    @PostMapping("/deleteUser")
    public ResponseEntity deleteUser(@RequestParam String email) {
        if (userService.deleteUser(email)) {
            return new ResponseEntity("user was deleted", HttpStatus.ACCEPTED);
        }

        return new ResponseEntity("error", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/loginUser")
    public ResponseEntity loginUser(@RequestBody User user) {
        if (userService.loginUser(user)) {
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            String access_token = JWT.create()
                    .withSubject(user.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
                    .withClaim("roles", userService.getAuthority(userService.getByEmail(user.getEmail())).stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .sign(algorithm);

            return ResponseEntity.ok().body(access_token);
        } else
            return ResponseEntity.badRequest().body("incorrect email or password");
    }

    //
    @GetMapping("/get")
    public ResponseEntity getAll() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getByEmail")
    public ResponseEntity getByEmail(@RequestParam String email) {
        User user = userService.getByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getUser")
    public ResponseEntity getUser(@RequestHeader(value = "Authorization") String authorization) {
        User user = userService.getByToken(authorization);
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getPositions")
    public ResponseEntity<List<Position>> getAllPositions(){
        List<Position> positions = positionService.getAllPositions();
        return ResponseEntity.ok(positions);
    }

    @GetMapping("/getDegrees")
    public ResponseEntity<List<Degree>> getAllDegrees(){
        List<Degree> degrees = degreeService.getAllDegrees();
        return ResponseEntity.ok(degrees);
    }

    @GetMapping("/getUserEmail")
    public ResponseEntity getUserEmail(@RequestHeader(value = "Authorization") String authorization) {
        User user = userService.getByToken(authorization);
        return ResponseEntity.ok(user.getEmail());
    }



//    @PostMapping("/changePhoto")
//    public ResponseEntity changePhoto(@RequestHeader(value = "Authorization") String authorization, @RequestBody String file) {
//        file = file.replace("{\"file\":\"", "");
//        file = file.replace("\"}", "");
//        if (userService.changePhoto(authorization, file)) {
//            return new ResponseEntity("photo was changed", HttpStatus.ACCEPTED);
//        }
//        return new ResponseEntity("error", HttpStatus.BAD_REQUEST);
//    }


}
