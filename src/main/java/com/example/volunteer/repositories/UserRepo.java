package com.example.volunteer.repositories;

import com.example.volunteer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
   public User findByEmail(String email);

}
