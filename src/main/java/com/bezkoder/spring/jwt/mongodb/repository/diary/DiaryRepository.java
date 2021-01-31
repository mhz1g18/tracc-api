package com.bezkoder.spring.jwt.mongodb.repository.diary;

import com.bezkoder.spring.jwt.mongodb.models.diary.Diary;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Qualifier("MongoRepo")
public interface DiaryRepository extends MongoRepository<Diary, String> {
}
