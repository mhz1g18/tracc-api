package com.tracc.repository.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tracc.models.user.ERole;
import com.tracc.models.user.Role;

@Qualifier("MongoRepo")
public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}
