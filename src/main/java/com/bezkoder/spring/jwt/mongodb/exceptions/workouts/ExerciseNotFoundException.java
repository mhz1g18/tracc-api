package com.bezkoder.spring.jwt.mongodb.exceptions.workouts;

public class ExerciseNotFoundException extends RuntimeException {
    public ExerciseNotFoundException(String id) {
        super("No exercise with id " + id + " found");
    }
}
