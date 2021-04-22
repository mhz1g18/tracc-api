package com.tracc.controllers.workouts;

import com.tracc.models.workouts.Exercise;
import com.tracc.security.services.workouts.ExercisesService;
import com.tracc.exceptions.workouts.ExerciseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/exercises/")
@PreAuthorize("isAuthenticated()")
public class ExercisesController {

    private final ExercisesService exercisesService;

    @Autowired
    public ExercisesController(ExercisesService exercisesService) {
        this.exercisesService = exercisesService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllAvailableExercises() {
        List<Exercise> exercises = exercisesService.findAllExercises();
        return ResponseEntity.ok(exercises);
    }

    /**
     * Return a Nutrition document with a specific id
     * @param id the id of the looked-up document
     * @return the matching Nutrition object
     * @throws ExerciseNotFoundException
     */

    @GetMapping("/{id}")
    public ResponseEntity<?> getExerciseById(@PathVariable String id) {
        Exercise exercise = exercisesService.findExerciseById(id);
        return ResponseEntity.ok(exercise);
    }

    /**
     * Add a new Nutrition document to the database
     * @param exercise a validated NutritionRequest containing all needed fields
     * @return the persisted Nutrition object
     */

    @PostMapping("/")
    public ResponseEntity<?> addExercise(@Valid @RequestBody  Exercise exercise) {
        return ResponseEntity.ok(exercisesService.addExercise(exercise));
    }

    /**
     * Delete a Nutrition document by finding them by their id
     * @param id the id of the Nutrition document to delete
     * @return a 204 No Content empty response message regardless
     *          if a Nutrition document was found and deleted or not
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExercise(@PathVariable String id) {
        exercisesService.deleteExerciseById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     *
     * @param id
     * @param newExercise
     * @return
     */

    @PutMapping("/{id}")
    public ResponseEntity<?> editExercise(@PathVariable String id, @Valid @RequestBody Exercise newExercise) {
        Exercise exercise = exercisesService.editExerciseById(id, newExercise);
        return ResponseEntity.ok(exercise);
    }

    /**
     * Handles queries searching for Nutrition by type
     * @param type the specified type
     * @return list of results
     */

    @GetMapping(value = "/search", params = "type")
    public ResponseEntity<?> findByType(@RequestParam String type) {
        return ResponseEntity.ok(exercisesService.findByType(type.toUpperCase()));
    }

    /**
     * Handles queries searching for Nutrition by name
     * @param name string to use a regex match on
     * @return list of results
     */

    @GetMapping(value = "/search", params = "name")
    public ResponseEntity<?> findByName(@RequestParam String name) {
        return ResponseEntity.ok(exercisesService.findByName(name));
    }

    @GetMapping(value = "/search", params = "category")
    public ResponseEntity<?> findByCategory(@RequestParam String category) {
        return ResponseEntity.ok(exercisesService.findByCategory(category.toUpperCase()));
    }





}
