package com.bezkoder.spring.jwt.mongodb.repository.workouts;

import com.bezkoder.spring.jwt.mongodb.models.diary.Diary;
import com.bezkoder.spring.jwt.mongodb.models.workouts.Workout;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WorkoutRepository extends MongoRepository<Workout, String> {

    @Query(" { 'userId' : ?0 } ")
    List<Workout> findByUserId(String userId);

    @Query("{ $or: [ { 'id': ?0, 'userId' : ?1 }, { 'id': ?0, 'userId' : 'ADMIN' } ] }")
    Optional<Workout> findByWorkoutId(String id, String userId);

    @Query(value = "{ 'id': ?0, 'userId' : ?1 }", delete = true)
    void deleteWorkoutById(String id, String userId);
}
