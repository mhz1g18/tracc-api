package com.bezkoder.spring.jwt.mongodb.repository.nutrition;

import com.bezkoder.spring.jwt.mongodb.models.nutrition.Nutrition;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

@Qualifier("MongoRepo")
public interface NutritionRepository extends MongoRepository<Nutrition, String> {

    /**
     * Fetches all nutrition available to user with id same as the parameter
     * Available nutrition to an user is created by the user or an ADMIN
     *
     * @param userId the user's id
     * @return list of nutrition
     */

    @Query("{ $or: [ { 'createdBy' : ?0 }, {  'createdBy': 'ADMIN' } ] } ")
    List<Nutrition> getAllNutrition(String userId);

    /**
     * Fetches specific nutrition by id
     * Available nutrition to an user is created by the user or an ADMIN
     *
     * @param id the nutrition's id
     * @param userId the user's id
     * @return Nutrition object wrapped in Optional in case
     *         the query did not return a result
     */

    @Query("{ $or: [ { 'id': ?0, 'createdBy' : ?1 }, { 'id': ?0, 'createdBy' : 'ADMIN' } ] }")
    Optional<Nutrition> getNutrityionById(String id, String userId);

    /**
     * Deletes specific nutrition by id
     * A user can delete nutrition created by the user or an ADMIN
     *
     * @param id the nutrition's id
     * @param userId the user's id
     * @return Nutrition object wrapped in Optional in case
     *         the query did not find a match to delete
     */

    @Query(value = "{ 'id': ?0, 'createdBy' : ?1 }", delete = true)
    void deleteNutrityionById(String id, String userId);

    /**
     * Fetches all Nutrition available to a user by type
     *
     * @return List of results
     */

    @Query("{ 'type': ?0 }")
    List<Nutrition> findNutritionByType(String type);

    /**
     * Searches for Nutrition based on 'name' key
     *
     * @return List of results
     */

    // " { $and [ { 'name': { $regex: ?0, $options:'i' } }, $or: [ { 'id': ?0, 'createdBy' : ?1 }, { 'id': ?0, 'createdBy' : 'ADMIN' } ] ] }"
    @Query("{ 'name': { $regex: ?0, $options:'i' } }") // add filtering such as getAllNutrition ??
    List<Nutrition> findByName(String name);




}