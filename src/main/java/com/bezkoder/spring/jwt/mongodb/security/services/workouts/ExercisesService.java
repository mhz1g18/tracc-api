package com.bezkoder.spring.jwt.mongodb.security.services.workouts;

import com.bezkoder.spring.jwt.mongodb.exceptions.nutrition.NutritionNotFoundException;
import com.bezkoder.spring.jwt.mongodb.exceptions.workouts.ExerciseNotFoundException;
import com.bezkoder.spring.jwt.mongodb.models.nutrition.Nutrition;
import com.bezkoder.spring.jwt.mongodb.models.nutrition.NutritionCategory;
import com.bezkoder.spring.jwt.mongodb.models.workouts.Exercise;
import com.bezkoder.spring.jwt.mongodb.payload.request.nutrition.NutritionRequest;
import com.bezkoder.spring.jwt.mongodb.repository.workouts.ExersiseRepository;
import com.bezkoder.spring.jwt.mongodb.security.jwt.UserProfileUtils;
import com.bezkoder.spring.jwt.mongodb.security.services.user.UserDetailsImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExercisesService {

    private final ExersiseRepository exersiseRepository;
    private final UserProfileUtils userProfileUtils;

    @Autowired
    public ExercisesService(ExersiseRepository exersiseRepository,
                            UserProfileUtils userProfileUtils) {
        this.exersiseRepository = exersiseRepository;
        this.userProfileUtils = userProfileUtils;
    }

    /**
     * Fetch all exercise documents created by admin or the user
     * Note: does not include Exercise documents not created by
     *       the user issuing the request or an admin
     *
     * @return list of Exercise documents created by admin or the user
     */

    public List<Exercise> findAllExercises() {
        String userId = userProfileUtils.getUserDetails().getId();
        return exersiseRepository.getAllExercises(userId);
    }

    /**
     * Fetch Nutrition document by the _id field
     * Results are not limited the documents created by the user or admin
     * Note: Users must be able to fetch individual documents after a search
     *
     * @param id the document's _id field
     * @return the Nutrition document with the specified id
     * @throws NutritionNotFoundException
     */

    public Exercise findExerciseById(String id) {
        return exersiseRepository.findById(id)
                .orElseThrow(() -> new ExerciseNotFoundException(id));
    }

    /**
     * Handles a NutritionRequest to construct a Nutrition object
     * and persist it to tha Nutrition collection in the database.
     * A NutritionRequest may be a FoodRequest or a SupplementRequest
     *
     * @param exercise the incoming request
     * @return the persisted Nutrition object
     * @see NutritionRequest
     * @see this.parseNutritionRequest()
     */

    public Exercise addExercise(Exercise exercise) {

        /**
         * Account's with authority role of an admin have
         * the purpose of only populating the datbase with "default" data
         *
         * Nutrition documents tagged as cready_by admin serve
         * as the default nutrition list of an user
         */

        Collection<? extends GrantedAuthority>  authorities = userProfileUtils.getUserAuthorities();

        if(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            exercise.setCreatedBy("ADMIN");
        } else {
            UserDetailsImpl userDetails = userProfileUtils.getUserDetails();
            exercise.setCreatedBy(userDetails.getId());
        }

        return exersiseRepository.save(exercise);

    }

    /**
     * Delete a nutrition document by its id
     * A user may only delete personally created documents
     *
     * @param id of the document to delete
     */

    public void deleteExerciseById(String id) {
        String userId = userProfileUtils.getUserDetails().getId();

        if(userProfileUtils.getUserAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
            userId = "ADMIN";

        exersiseRepository.deleteExerciseById(id, userId);

    }

    /**
     * Edit a nutrition document by its id
     * A user may only edit personally created documents
     *
     * @param id of the document to edit
     * @param newExerciseRequest modified version of the document
     * @return the persisted object
     */

    public Exercise editExerciseById(String id, Exercise newExerciseRequest) {

        String userId = userProfileUtils.getUserDetails().getId();
        Exercise existing = exersiseRepository.getExerciseById(id, userId)
                .orElseThrow(() -> new ExerciseNotFoundException(id));

        Exercise newExercise = newExerciseRequest;

        newExercise.setId(id);
        newExercise.setCreatedBy(existing.getCreatedBy());

       return exersiseRepository.save(newExercise);

    }

    public List<Exercise> findByType(String type) {
        return exersiseRepository.findExerciseByType(type);
    }

    public List<Exercise> findByName(String name) {
        return exersiseRepository.findByName(".*" + name + ".*");
    }

    public List<Exercise> findByCategory(String category) {
        return exersiseRepository.findByCategory(category);
    }




}
