package com.bezkoder.spring.jwt.mongodb.repository.user;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bezkoder.spring.jwt.mongodb.models.user.User;
import org.springframework.stereotype.Repository;

@Repository("MongoRepo")
public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
