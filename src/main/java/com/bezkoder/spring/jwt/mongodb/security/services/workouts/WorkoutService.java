package com.bezkoder.spring.jwt.mongodb.security.services.workouts;

import com.bezkoder.spring.jwt.mongodb.exceptions.workouts.ExerciseNotFoundException;
import com.bezkoder.spring.jwt.mongodb.exceptions.workouts.WorkoutNotFoundException;
import com.bezkoder.spring.jwt.mongodb.models.nutrition.Nutrition;
import com.bezkoder.spring.jwt.mongodb.models.nutrition.NutritionCategory;
import com.bezkoder.spring.jwt.mongodb.models.user.UserProfile;
import com.bezkoder.spring.jwt.mongodb.models.workouts.Exercise;
import com.bezkoder.spring.jwt.mongodb.models.workouts.Workout;
import com.bezkoder.spring.jwt.mongodb.repository.workouts.WorkoutRepository;
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

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserProfileUtils userProfileUtils;

    @Autowired
    public WorkoutService(WorkoutRepository workoutRepository,
                          UserProfileUtils userProfileUtils) {
        this.workoutRepository = workoutRepository;
        this.userProfileUtils = userProfileUtils;
    }

    public List<Workout> getUserWorkouts() {
        String userId = userProfileUtils.getUserDetails().getId();

        return workoutRepository.findByUserId(userId);

    }

    public Workout getWorkoutById(String id) {
        String userId = userProfileUtils.getUserDetails().getId();

        return workoutRepository.findByWorkoutId(id, userId)
                                .orElseThrow(() -> new WorkoutNotFoundException(id));
    }

    public Workout addWorkout(Workout workout) {
        Collection<? extends GrantedAuthority> authorities = userProfileUtils.getUserAuthorities();

        if(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            workout.setUserId("ADMIN");
        } else {
            UserDetailsImpl userDetails = userProfileUtils.getUserDetails();
            workout.setUserId(userDetails.getId());
        }

        return workoutRepository.save(workout);
    }

    public void deleteWorkoutById(String id) {
        String userId = userProfileUtils.getUserDetails().getId();

        if(userProfileUtils.getUserAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
            userId = "ADMIN";

        workoutRepository.deleteWorkoutById(id, userId);

    }

    public Workout editWorkoutById(String id, Workout newExerciseRequest) {

        String userId = userProfileUtils.getUserDetails().getId();
        Workout existing = workoutRepository.findByWorkoutId(id, userId)
                    .orElseThrow(() -> new WorkoutNotFoundException(id));

        Workout newWorkout = newExerciseRequest;

        newWorkout.setId(id);
        newWorkout.setUserId(existing.getUserId());

        return workoutRepository.save(newWorkout);

    }

}
