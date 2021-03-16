package com.bezkoder.spring.jwt.mongodb.models.workouts;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StrengthExercise extends Exercise {

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Map<Integer, Integer> setsAndReps = new HashMap<>();

    private EStrengthCategory category;

    public StrengthExercise() {
        super("STRENGTH");
    }

    public Map<Integer, Integer> getSetsAndReps() {
        return setsAndReps;
    }

    public void setSetsAndReps(Map<Integer, Integer> setsAndReps) {
        this.setsAndReps = setsAndReps;
    }

    public EStrengthCategory getCategory() {
        return category;
    }

    public void setCategory(EStrengthCategory category) {
        this.category = category;
    }
}


