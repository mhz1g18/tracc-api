package com.bezkoder.spring.jwt.mongodb.security.services.user;

import com.bezkoder.spring.jwt.mongodb.models.user.ERole;
import com.bezkoder.spring.jwt.mongodb.models.user.Role;
import com.bezkoder.spring.jwt.mongodb.repository.user.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(@Qualifier("MongoRepo") RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    public Optional<Role> findByName(ERole name) {
        return roleRepository.findByName(name);
    }




}
