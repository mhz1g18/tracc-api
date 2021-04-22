package com.tracc.security.services.workouts;

import com.tracc.exceptions.workouts.WorkoutNotFoundException;
import com.tracc.models.workouts.Workout;
import com.tracc.repository.workouts.WorkoutRepository;
import com.tracc.security.jwt.UserProfileUtils;
import com.tracc.security.services.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

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
