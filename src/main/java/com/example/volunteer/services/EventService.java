package com.example.volunteer.services;

import com.auth0.jwt.JWT;
import com.example.volunteer.entities.Event;
import com.example.volunteer.entities.User;
import com.example.volunteer.modules.UpdateDataEvent;
import com.example.volunteer.repositories.EventRepo;
import com.example.volunteer.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EventService {
    private final EventRepo eventRepo;

    private final UserRepo userRepo;

    public boolean createEvent(Event event, String token){
        String email = JWT.decode(token).getSubject();
        User user = userRepo.findByEmail(email);
        event.setFinished(false);
        event.setOrganizer(user);
        eventRepo.save(event);
        return true;
    }

    public List<Event> getEvents(){
        return eventRepo.findAll();
    }

    public Event getEventById(Long id){
        Event event = eventRepo.findById(id).orElse(null);
        return event;
    }

    public boolean deleteEvent(Long id){
        Event event = eventRepo.findById(id).orElse(null);
        if(event == null){
            return false;
        }
        eventRepo.delete(event);
        return true;
    }
    public boolean updateDataEvent(UpdateDataEvent newData, Long id){
        Event event = eventRepo.findById(id).orElse(null);
        if(event != null){
            if (!newData.getName().equals("")) {
                event.setName(newData.getName());
            }
            if (!newData.getCity().equals("")) {
                event.setCity(newData.getCity());
            }
            if (!newData.getTime().equals("")) {
                event.setTime(newData.getTime());
            }
            if (newData.getAmountOfVolunteer()!=null) {
                event.setAmountOfVolunteer(newData.getAmountOfVolunteer());
            }
            if (newData.getDate() != null) {
                event.setDate(newData.getDate());
            }
            if (!newData.getDescription().equals("")) {
                event.setDescription(newData.getDescription());
            }
            if(!(newData.getImage() == null)){
                event.setImage(newData.getImage());
            }
            if(!(newData.getLat() == 0) && !(newData.getLng() == 0)){
                event.setLat(newData.getLat());
                event.setLng(newData.getLng());
            }
            eventRepo.save(event);
            return true;
        }
        return false;
    }
    public boolean finishEvent(Long id){
        Event event = eventRepo.findById(id).orElse(null);
        if(event == null)return false;
        event.setFinished(true);
        return true;
    }
    public boolean checkUserInEvent(String token,Long id_event){
        String email = JWT.decode(token).getSubject();
        User user = userRepo.findByEmail(email);
        boolean has = eventRepo.existsByParticipantsAndId(user,id_event);
        return has;
    }

}
