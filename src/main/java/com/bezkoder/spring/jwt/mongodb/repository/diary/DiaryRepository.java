package com.bezkoder.spring.jwt.mongodb.repository.diary;

import com.bezkoder.spring.jwt.mongodb.models.diary.Diary;
import com.bezkoder.spring.jwt.mongodb.models.diary.DiaryEntryList;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Qualifier("MongoRepo")
public interface DiaryRepository extends MongoRepository<Diary, String> {

    @Query(" { 'userId' : ?0 } ")
    Optional<Diary> findByUserId(String userId);



}
