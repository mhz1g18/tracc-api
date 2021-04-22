package com.tracc.exceptions.workouts;

public class ExerciseNotFoundException extends RuntimeException {
    public ExerciseNotFoundException(String id) {
        super("No exercise with id " + id + " found");
    }
}
