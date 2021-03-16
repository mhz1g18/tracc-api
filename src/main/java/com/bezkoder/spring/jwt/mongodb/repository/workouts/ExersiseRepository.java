package com.bezkoder.spring.jwt.mongodb.repository.workouts;

import com.bezkoder.spring.jwt.mongodb.models.nutrition.Nutrition;
import com.bezkoder.spring.jwt.mongodb.models.workouts.Exercise;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExersiseRepository extends MongoRepository<Exercise, String> {

    /**
     * Fetches all exercises available to user with id same as the parameter
     * Available exercises to an user is created by the user or an ADMIN
     *
     * @param userId the user's id
     * @return list of nutrition
     */

    @Query("{ $or: [ { 'createdBy' : ?0 }, {  'createdBy': 'ADMIN' } ] } ")
    List<Exercise> getAllExercises(String userId);

    /**
     * Fetches specific exercise by id
     * Available exercise to an user is created by the user or an ADMIN
     *
     * @param id the exercise's id
     * @param userId the user's id
     * @return Nutrition object wrapped in Optional
     */

    @Query("{ $or: [ { 'id': ?0, 'createdBy' : ?1 }, { 'id': ?0, 'createdBy' : 'ADMIN' } ] }")
    Optional<Exercise> getExerciseById(String id, String userId);

    /**
     * Deletes specific exercise by id
     * A user can delete nutrition created by the user
     *
     * @param id the nutrition's id
     * @param userId the user's id
     * @return Nutrition object wrapped in Optional in case
     *         the query did not find a match to delete
     */

    @Query(value = "{ 'id': ?0, 'createdBy' : ?1 }", delete = true)
    void deleteExerciseById(String id, String userId);

    /**
     * Fetches all Exercises available to a user by type
     *
     * @return List of results
     */

    @Query("{ 'type': ?0 }")
    List<Exercise> findExerciseByType(String type);

    /**
     * Searches for Exercise  performing a
     * regex based search on the name field
     * search is case insensitive
     *
     * @return List of results
     */

    @Query("{ 'name': { $regex: ?0, $options:'i' } }")
    List<Exercise> findByName(String name);

    @Query("{ 'category': ?0 }")
    public List<Exercise> findByCategory(String category);

}
