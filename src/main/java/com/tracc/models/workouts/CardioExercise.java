package com.tracc.models.workouts;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CardioExercise extends Exercise{

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private float duration;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private float distance;

    public CardioExercise() {
        super("CARDIO");
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}
