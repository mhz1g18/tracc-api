package com.bezkoder.spring.jwt.mongodb.security.services.user;

import com.bezkoder.spring.jwt.mongodb.models.user.User;
import com.bezkoder.spring.jwt.mongodb.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(@Qualifier("MongoRepo") UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<User> findById(String id) { return userRepository.findById(id); }

    public void save(User user) {
        userRepository.save(user);
    }


}
