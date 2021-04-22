package com.tracc.payload.request.diary;

import com.tracc.models.workouts.Exercise;

import java.util.List;

public class WorkoutDiaryEntryRequest extends DiaryEntryRequest {

    private String name;
    private List<Exercise> exerciseList;

    public WorkoutDiaryEntryRequest() {
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
