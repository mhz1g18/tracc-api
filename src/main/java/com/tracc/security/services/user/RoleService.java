package com.tracc.security.services.user;

import com.tracc.models.user.ERole;
import com.tracc.models.user.Role;
import com.tracc.repository.user.RoleRepository;
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
