package com.example.volunteer.repositories;

import com.example.volunteer.entities.Event;
import com.example.volunteer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepo extends JpaRepository<Event,Long> {
     List<Event> findByOrganizer(User user);

     List<Event> findAllByParticipants(User user);

     boolean existsByParticipantsAndId(User user,Long id);

     List<Event> findAllByOrganizer(User user);

}
