package com.bezkoder.spring.jwt.mongodb.repository.userprofile;

import com.bezkoder.spring.jwt.mongodb.models.user.UserProfile;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;

@Qualifier("MongoRepo")
public interface UserProfileRepository extends MongoRepository<UserProfile, String> {

}
