package com.bezkoder.spring.jwt.mongodb.repository.userprofile;

import com.bezkoder.spring.jwt.mongodb.models.user.UserProfile;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Qualifier("MongoRepo")
public interface UserProfileRepository extends MongoRepository<UserProfile, String> {

    @Query("{ 'userId': ?0 }")
    public Optional<UserProfile> findByUserId(String userId);

}
