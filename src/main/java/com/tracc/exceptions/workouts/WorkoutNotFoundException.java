package com.tracc.exceptions.workouts;

public class WorkoutNotFoundException extends RuntimeException {
    public WorkoutNotFoundException(String id) {
        super("No workout with id " + id + " found");
    }
}
