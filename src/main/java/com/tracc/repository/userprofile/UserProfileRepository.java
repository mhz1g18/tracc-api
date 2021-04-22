package com.tracc.repository.userprofile;

import com.tracc.models.user.UserProfile;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

@Qualifier("MongoRepo")
public interface UserProfileRepository extends MongoRepository<UserProfile, String> {

    @Query("{ 'userId': ?0 }")
    public Optional<UserProfile> findByUserId(String userId);

}
