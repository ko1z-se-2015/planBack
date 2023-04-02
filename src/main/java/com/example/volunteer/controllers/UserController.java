package com.example.volunteer.controllers;


import com.auth0.jwt.JWT;

import com.auth0.jwt.algorithms.Algorithm;

import com.example.volunteer.entities.Event;
import com.example.volunteer.entities.User;
import com.example.volunteer.modules.UpdateDataUser;
import com.example.volunteer.services.UserService;
import com.example.volunteer.storage.ValidationError;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.util.*;
import java.util.stream.Collectors;

//
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createVolunteer")
    public ResponseEntity createVolunteer(@Valid  @RequestBody User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<ValidationError> validationErrors = new ArrayList<>();
            for (FieldError error : errors ) {
                validationErrors.add(new ValidationError(error.getField(),error.getDefaultMessage()));
            }
            return ResponseEntity.badRequest().body(validationErrors);
        }
             if(userService.createVolunteer(user)){
                 return new ResponseEntity("user was added", HttpStatus.CREATED);
             }

        return new ResponseEntity("user wasn't added", HttpStatus.BAD_REQUEST);
    }
//
    @PostMapping("/createOrganizer")
    public ResponseEntity createOrganizer(@Valid @RequestBody  User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<ValidationError> validationErrors = new ArrayList<>();
            for (FieldError error : errors ) {
                validationErrors.add(new ValidationError(error.getField(),error.getDefaultMessage()));
            }
            return ResponseEntity.badRequest().body(validationErrors);
        }
        if(userService.createOrganizer(user)){
            return new ResponseEntity("user was added", HttpStatus.CREATED);
        }

        return new ResponseEntity("user wasn't added", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/updateDataUser")
    public ResponseEntity updateDataUser(@RequestHeader(value = "Authorization") String authorization,
                                         @Valid @RequestBody UpdateDataUser updateDataUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<ValidationError> validationErrors = new ArrayList<>();
            for (FieldError error : errors) {
                validationErrors.add(new ValidationError(error.getField(), error.getDefaultMessage()));
            }
            return ResponseEntity.ok(validationErrors);
        }

        User user = userService.getByToken(authorization);
        if (userService.updateDataUser(user.getEmail(), updateDataUser)) {
            return new ResponseEntity("data was updated", HttpStatus.ACCEPTED);
        }

        return new ResponseEntity("error", HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/deleteUser")
    public ResponseEntity deleteUser(@RequestParam String email){
        if(userService.deleteUser(email)){
            return new ResponseEntity("user was deleted",HttpStatus.ACCEPTED);
        }

        return new ResponseEntity("error",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/loginUser")
    public ResponseEntity loginUser(@RequestBody User user){
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
    public  ResponseEntity getAll(){
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getByEmail")
    public  ResponseEntity getByEmail(@RequestParam String email){
        User user = userService.getByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getUser")
    public  ResponseEntity getUser(@RequestHeader(value="Authorization") String authorization){
        User user = userService.getByToken(authorization);
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getNumUser")
    public  ResponseEntity getNumUser(){
        int num = userService.getNumUsers();
        return ResponseEntity.ok(num);
    }
    @GetMapping("/getUserEmail")
    public  ResponseEntity getUserEmail(@RequestHeader(value="Authorization") String authorization){
        User user = userService.getByToken(authorization);
        return ResponseEntity.ok(user.getEmail());
    }
    @PostMapping("/joinToEvent")
    public ResponseEntity joinToEvent(@RequestHeader(value="Authorization") String authorization,@RequestParam Long id_event ){
      if(userService.joinEvent(authorization,id_event)){
          return  new ResponseEntity("user joined",HttpStatus.ACCEPTED);
      }
      return new ResponseEntity("error",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/leaveFromEvent")
    public ResponseEntity leaveFromEvent(@RequestHeader(value="Authorization") String authorization,@RequestParam Long id_event){
        if(userService.leaveEvent(authorization,id_event)){
            return new ResponseEntity("user left from event",HttpStatus.ACCEPTED);
        }
        return new ResponseEntity("error",HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/find")
    public ResponseEntity myEvents(@RequestHeader(value="Authorization") String authorization){
        List<Event> events = userService.myEvents(authorization);
        return ResponseEntity.accepted().body(events);
    }
    @GetMapping("/myCreatedEvent")
    public ResponseEntity myCreatedEvent(@RequestHeader(value="Authorization") String authorization){
        List<Event> events = userService.myCreatedEvent(authorization);
        return ResponseEntity.ok(events);
    }
    @GetMapping("/myJoinedEvent")
    public ResponseEntity myJoinedEvent(@RequestHeader(value="Authorization") String authorization){
        List<Event> events = userService.myEvents(authorization);
        return ResponseEntity.ok(events);
    }
    @PostMapping("/changePhoto")
    public ResponseEntity changePhoto(@RequestHeader(value="Authorization") String authorization,@RequestBody String file){
        file = file.replace("{\"file\":\"", "");
        file = file.replace("\"}", "");
        if(userService.changePhoto(authorization,file)){
            return new ResponseEntity("photo was changed",HttpStatus.ACCEPTED);
        }
        return new ResponseEntity("error",HttpStatus.BAD_REQUEST);
    }


}
