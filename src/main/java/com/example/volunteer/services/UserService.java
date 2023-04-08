package com.example.volunteer.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.volunteer.entities.Event;
import com.example.volunteer.entities.User;
import com.example.volunteer.modules.UpdateDataUser;
import com.example.volunteer.repositories.EventRepo;
import com.example.volunteer.repositories.RoleRepo;
import com.example.volunteer.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;

    private final RoleRepo roleRepo;

    private final EventRepo eventRepo;


    public final PasswordEncoder passwordEncoder;

    public boolean createVolunteer(User user) {
        if (userRepo.findByEmail(user.getEmail()) != null) {
            return false;
        }
        user.getRoles().add(roleRepo.findById(1L).orElse(null));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        return true;
    }

    public boolean createOrganizer(User user) {
        if (userRepo.findByEmail(user.getEmail()) != null) {
            return false;
        }
        user.getRoles().add(roleRepo.findById(2L).orElse(null));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        return true;
    }

    public boolean loginUser(User data) {
        User user = userRepo.findByEmail(data.getEmail());
        if (user == null) return false;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(data.getPassword(), user.getPassword())) return false;
        return true;
    }

    public Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });
        return authorities;
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public User getByEmail(String email) {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            return null;
        }
        return user;
    }

    public int getNumUsers() {
        return userRepo.findAll().size();
    }

    public User getByToken(String token) {
        String email = JWT.decode(token).getSubject();
        User user = userRepo.findByEmail(email);
        if (user == null) {
            return null;
        }
        return user;
    }



    public boolean deleteUser(String token) {
        String email = JWT.decode(token).getSubject();
        User user = userRepo.findByEmail(email);
        if (user == null) {
            return false;
        }
        userRepo.delete(user);
        return true;
    }

    public boolean joinEvent(String token, Long id_event) {
        String email = JWT.decode(token).getSubject();
        User user = userRepo.findByEmail(email);
        if (user == null) {
            return false;
        }
        Event event = eventRepo.findById(id_event).orElse(null);
        if (event == null) {
            return false;
        }
        event.getParticipants().add(user);
        return true;
    }

    public boolean leaveEvent(String token, Long id_event) {
        String email = JWT.decode(token).getSubject();
        User user = userRepo.findByEmail(email);
        if (user == null) {
            return false;
        }

        Event event = eventRepo.findById(id_event).orElse(null);
        if (event == null) {
            return false;
        }
        event.getParticipants().remove(user);
        return true;
    }

    public List<Event> myEvents(String token) {
        String email = JWT.decode(token).getSubject();
        User user = userRepo.findByEmail(email);
        return eventRepo.findAllByParticipants(user);
    }

    public List<Event> myCreatedEvent(String token) {
        String email = JWT.decode(token).getSubject();
        User user = userRepo.findByEmail(email);
        List<Event> events = eventRepo.findAllByOrganizer(user);
        return events;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            log.error("User not found!");
            throw new UsernameNotFoundException("User not found!");
        } else {
            log.info("User found in the database: {}", email);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

}
