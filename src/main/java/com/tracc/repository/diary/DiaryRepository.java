package com.tracc.repository.diary;

import com.tracc.models.diary.Diary;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

@Qualifier("MongoRepo")
public interface DiaryRepository extends MongoRepository<Diary, String> {

    @Query(" { 'userId' : ?0 } ")
    Optional<Diary> findByUserId(String userId);



}
