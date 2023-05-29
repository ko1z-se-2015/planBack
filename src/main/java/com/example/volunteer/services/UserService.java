package com.example.volunteer.services;


import com.auth0.jwt.JWT;
import com.example.volunteer.entities.Degree;
import com.example.volunteer.entities.Position;
import com.example.volunteer.entities.User;
import com.example.volunteer.modules.ResetPassword;
import com.example.volunteer.repositories.RoleRepo;
import com.example.volunteer.repositories.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    private final PositionService positionService;
    private final DegreeService degreeService;

    private final DepartmentService departmentService;

    public final PasswordEncoder passwordEncoder;

    public String resetPassword(User user, ResetPassword resetPassword){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(resetPassword.getOldPassword(), user.getPassword())) return "Incorrect password";

        if (!resetPassword.getNewPassword().equals(resetPassword.getConfirmNewPassword())) return "New passwords do not match";

        user.setPassword(passwordEncoder.encode(resetPassword.getNewPassword()));
        userRepo.save(user);
        return "New password has been saved";
    }

    public void setNewPassword(User user, String password){
        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);
    }

    public boolean verify(User user) {
        if (!createTeacher(user)) return false;

        assignPositionByEmailAndName(user.getEmail(), "INSTRUCTOR");
        assignDegreeByEmailAndName(user.getEmail(), "TEACHER");
        user.setRate("1");

        departmentService.addTeacher(departmentService.findByName("Department of Computer Engineering"), user);
        return true;
    }

    public boolean createTeacher(User user) {
        if (userRepo.findByEmail(user.getEmail()) != null) {
            return false;
        }
        user.getRoles().add(roleRepo.findByRoleName("TEACHER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        return true;
    }

    public boolean createDirector(User user) {
        if (userRepo.findByEmail(user.getEmail()) != null) {
            return false;
        }
        user.getRoles().add(roleRepo.findByRoleName("DIRECTOR"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        return true;
    }

    public void update(User user) {
        userRepo.save(user);
    }
    //ASSIGNING THE USER'S DATA

    public boolean assignPositionByEmailAndName(String email, String positionName) {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            return false;
        }

        Position position = positionService.getPositionByName(positionName);
        if (position == null) {
            return false;
        }

        user.setPosition(position);
        userRepo.save(user);
        return true;
    }

    public boolean assignDegreeByEmailAndName(String email, String degreeName) {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            return false;
        }

        Degree degree = degreeService.getDegreeByName(degreeName);
        if (degree == null) {
            return false;
        }

        user.setDegree(degree);
        userRepo.save(user);
        return true;
    }


    //AUTHORIZING USER

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

    public User getByToken(String token) {
        String email = JWT.decode(token).getSubject();
        User user = userRepo.findByEmail(email);
        if (user == null) {
            return null;
        }
        return user;
    }

    public User getById(Long id) {
        User user = userRepo.getById(id);
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

    public String generateUserToken(User user) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(userJson)
                .setExpiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS256, "xfty7ygp");

        return jwtBuilder.compact();
    }

    public User decodeUserToken(String token) throws JsonProcessingException {
        Claims claims = Jwts.parser()
                .setSigningKey("xfty7ygp")
                .parseClaimsJws(token)
                .getBody();

        String userJson = claims.getSubject();

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(userJson, User.class);
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
