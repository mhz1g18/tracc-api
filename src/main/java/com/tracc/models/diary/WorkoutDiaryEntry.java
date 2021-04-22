package com.tracc.models.diary;

import com.tracc.models.workouts.Exercise;

import java.util.ArrayList;
import java.util.List;

public class WorkoutDiaryEntry extends DiaryEntry{

    private String name;
    private List<Exercise> exerciseList = new ArrayList<>();

    public WorkoutDiaryEntry() {
        super("ENTRY_WORKOUT");
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
