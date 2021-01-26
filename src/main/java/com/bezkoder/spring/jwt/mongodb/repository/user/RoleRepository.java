package com.bezkoder.spring.jwt.mongodb.repository.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.bezkoder.spring.jwt.mongodb.models.user.ERole;
import com.bezkoder.spring.jwt.mongodb.models.user.Role;

@Qualifier("MongoRepo")
public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}
