package com.tracc.models.workouts;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ExerciseStat {
    private int reps;
    private int weight;

    public ExerciseStat() {}
    public ExerciseStat(int reps, int weight) {
        this.reps = reps;
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}

public class StrengthExercise extends Exercise {

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Map<Integer, Integer> setsAndReps = new HashMap<>();

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private List<ExerciseStat> sets = new ArrayList<>();

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

    public List<ExerciseStat> getSets() {
        return sets;
    }

    public void setSets(List<ExerciseStat> sets) {
        this.sets = sets;
    }
}


