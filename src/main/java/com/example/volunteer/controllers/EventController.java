package com.example.volunteer.controllers;

import com.example.volunteer.entities.Event;
import com.example.volunteer.modules.UpdateDataEvent;
import com.example.volunteer.services.EventService;
import com.example.volunteer.storage.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/add")
    public ResponseEntity createEvent(@RequestHeader(value="Authorization") String authorization, @Valid @RequestBody Event event, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<ValidationError> validationErrors = new ArrayList<>();
            for (FieldError error : errors ) {
                log.info(error.getField() + " - " + error.getDefaultMessage());
                validationErrors.add(new ValidationError(error.getField(),error.getDefaultMessage()));
            }
            return ResponseEntity.ok(validationErrors);
        }
        String file = event.getImage();
        file = file.replace("{\"file\":\"", "");
        file = file.replace("\"}", "");
        event.setImage(file);
        if(eventService.createEvent(event, authorization)){
            return new ResponseEntity("event was added", HttpStatus.CREATED);
        }

        return new ResponseEntity("event wasn't added", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get")
    public ResponseEntity getAll(){
        List<Event> events = eventService.getEvents();
        return ResponseEntity.ok(events);
    }
    @GetMapping("/getById")
    public ResponseEntity getById(@RequestParam Long id){
        Event event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }
    @PostMapping("/delete")
    public ResponseEntity deleteEvent(@RequestParam Long id){
     if(eventService.deleteEvent(id)){
         return new ResponseEntity("event was deleted", HttpStatus.ACCEPTED);
     }
     return  new ResponseEntity("error",HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/updateEvent")
    public ResponseEntity updateEvent (@RequestParam Long id,@Valid @RequestBody UpdateDataEvent updateDataEvent,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<ValidationError> validationErrors = new ArrayList<>();
            for (FieldError error : errors ) {
                log.info(error.getField() + " - " + error.getDefaultMessage());
                validationErrors.add(new ValidationError(error.getField(),error.getDefaultMessage()));
            }
            return ResponseEntity.badRequest().body(validationErrors);
        }
        if(!(updateDataEvent.getImage() == null)){
            String file = updateDataEvent.getImage();
            file = file.replace("{\"file\":\"", "");
            file = file.replace("\"}", "");
            updateDataEvent.setImage(file);
        }

        if(eventService.updateDataEvent(updateDataEvent,id)){
            return new ResponseEntity("event was updated", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity("error",HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/finish")
    public ResponseEntity finishEvent(@RequestParam Long id){
        if(eventService.finishEvent(id)){
            return new ResponseEntity("event was finished", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity("error",HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/check")
    public  ResponseEntity checkUserInEvent(@RequestHeader(value = "Authorization") String token, @RequestParam Long id_event){
        boolean has = eventService.checkUserInEvent(token,id_event);
        return ResponseEntity.accepted().body(has);
    }
}
