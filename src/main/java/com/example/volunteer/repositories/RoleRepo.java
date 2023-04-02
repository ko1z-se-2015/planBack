package com.example.volunteer.repositories;

import com.example.volunteer.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Long> {
   Role findByRoleName(String role);
}
