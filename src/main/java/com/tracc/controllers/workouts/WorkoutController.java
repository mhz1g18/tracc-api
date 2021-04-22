package com.tracc.controllers.workouts;

import com.tracc.models.workouts.Workout;
import com.tracc.security.services.workouts.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/workouts")
@PreAuthorize("isAuthenticated()")
public class WorkoutController {

    private final WorkoutService workoutService;

    @Autowired
    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getUserWorkouts() {
        List<Workout> workouts = workoutService.getUserWorkouts();
        return ResponseEntity.ok(workouts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkoutById(@PathVariable String id) {
        Workout workout = workoutService.getWorkoutById(id);
        return ResponseEntity.ok(workout);
    }

    @PostMapping("/")
    public ResponseEntity<?> addWorkout(@RequestBody @Valid Workout workout) {
        return ResponseEntity.ok(workoutService.addWorkout(workout));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExercise(@PathVariable String id) {
        workoutService.deleteWorkoutById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     *
     * @param id
     * @param newWorkout
     * @return
     */

    @PutMapping("/{id}")
    public ResponseEntity<?> editExercise(@PathVariable String id, @Valid @RequestBody Workout newWorkout) {
        Workout workout = workoutService.editWorkoutById(id, newWorkout);
        return ResponseEntity.ok(workout);
    }
}
