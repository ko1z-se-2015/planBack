package com.example.volunteer.controllers;


import com.auth0.jwt.JWT;

import com.auth0.jwt.algorithms.Algorithm;

import com.example.volunteer.entities.Degree;
import com.example.volunteer.entities.Position;
import com.example.volunteer.entities.User;
import com.example.volunteer.modules.ResetPassword;
import com.example.volunteer.services.DegreeService;
import com.example.volunteer.services.EmailNotificationService;
import com.example.volunteer.services.PositionService;
import com.example.volunteer.services.UserService;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
    private final EmailNotificationService emailNotificationService;

    public UserController(UserService userService, PositionService positionService, DegreeService degreeService, EmailNotificationService emailNotificationService) {
        this.userService = userService;
        this.positionService = positionService;
        this.degreeService = degreeService;
        this.emailNotificationService = emailNotificationService;
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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) throws JsonProcessingException {

        if (userService.getByEmail(user.getEmail()) != null){
            return new ResponseEntity<>("User is already exist", HttpStatus.BAD_REQUEST);
        }

        String token = userService.generateUserToken(user);

        String subject = "Email Verification";
        String text = "Please click the following link to verify your email: https://ipp-aitu.herokuapp.com/user/verify?token=" + token;
        emailNotificationService.sendSimpleMessage(user.getEmail(), subject, text);

        return new ResponseEntity<>("Verification mail has been sent", HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam String token) throws JsonProcessingException {
        User user = userService.decodeUserToken(token);

        if (user != null) {
            if (userService.verify(user)) {
                return ResponseEntity.status(HttpStatus.FOUND)
                        .location(UriComponentsBuilder.fromUriString("https://diploma-kappa.vercel.app/login")
                                .build().toUri())
                        .build();
            }
            else
                return new ResponseEntity<>("User is already exist", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestHeader(value = "Authorization") String authorization,
                                           @RequestBody ResetPassword resetPassword){
        User user = userService.getByToken(authorization);

        String response = userService.resetPassword(user, resetPassword);
        if (response.equals("New password has been saved"))
            return new ResponseEntity<>(response, HttpStatus.OK);
        else return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestParam String email,
                                            @RequestBody ResetPassword resetPassword) throws JsonProcessingException {
        User user = userService.getByEmail(email);
        if (user == null) return new ResponseEntity<>("User has not found", HttpStatus.BAD_REQUEST);

        if (!resetPassword.getNewPassword().equals(resetPassword.getConfirmNewPassword())) {
            return new ResponseEntity<>("New passwords do not match", HttpStatus.BAD_REQUEST);
        }

        User temp = new User();
        temp.setEmail(user.getEmail());
        temp.setPassword(resetPassword.getNewPassword());

        String token = userService.generateUserToken(temp);

        String subject = "Resetting the password";
        String text = "Please click the following link to reset your password: https://ipp-aitu.herokuapp.com/user/verifyReset?token=" + token;
        emailNotificationService.sendSimpleMessage(user.getEmail(), subject, text);

        return new ResponseEntity<>("Password reset mail has been sent", HttpStatus.OK);
    }

    @GetMapping("/verifyReset")
    public ResponseEntity<?> forgotPasswordVerify(@RequestParam String token) throws JsonProcessingException {
        User temp = userService.decodeUserToken(token);
        User user = userService.getByEmail(temp.getEmail());
        String password = temp.getPassword();

        if (user != null) {
            userService.setNewPassword(user, password);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(UriComponentsBuilder.fromUriString("https://diploma-kappa.vercel.app/login")
                            .build().toUri())
                    .build();
        } else {
            return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
        }
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
