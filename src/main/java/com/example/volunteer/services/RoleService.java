package com.example.volunteer.services;


import com.example.volunteer.entities.Role;
import com.example.volunteer.repositories.RoleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleService {

    private final RoleRepo roleRepo;

    public void addRole(String role){
        Role role1 = new Role(role);

        roleRepo.save(role1);
    }

}
