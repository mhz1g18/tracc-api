package com.bezkoder.spring.jwt.mongodb.repository.diary;

import com.bezkoder.spring.jwt.mongodb.models.diary.DiaryEntry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

@Qualifier("MongoRepo")
public interface DiaryEntryRepository extends MongoRepository<DiaryEntry, String> {

    @Query(value = "{ 'id': ?0, 'createdBy' : ?1 }", delete = true)
    void deleteDiaryEntryById(String id, String userId);

    @Query(value = "{ 'id': ?0, 'createdBy' : ?1 }")
    Optional<DiaryEntry> getDiaryEntryById(String id, String userId);
}
