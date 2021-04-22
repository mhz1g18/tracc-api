package com.tracc.models.user;

public class UserGoals {

    private int stepPerDayGoal;
    private int caloriesPerDayGoal;
    private int workoutsPerWeekGoal;
    private float sleepPerNightGoal;
    private float weightGoal;

    public UserGoals() {}

    public UserGoals(int stepPerDayGoal,
                     int caloriesPerDayGoal,
                     int workoutsPerWeekGoal,
                     int sleepPerNightGoal,
                     float weightGoal) {
        this.stepPerDayGoal = stepPerDayGoal;
        this.caloriesPerDayGoal = caloriesPerDayGoal;
        this.workoutsPerWeekGoal = workoutsPerWeekGoal;
        this.sleepPerNightGoal = sleepPerNightGoal;
        this.weightGoal = weightGoal;

    }

    public int getStepPerDayGoal() {
        return stepPerDayGoal;
    }

    public void setStepPerDayGoal(int stepPerDayGoal) {
        this.stepPerDayGoal = stepPerDayGoal;
    }

    public int getCaloriesPerDayGoal() {
        return caloriesPerDayGoal;
    }

    public void setCaloriesPerDayGoal(int caloriesPerDayGoal) {
        this.caloriesPerDayGoal = caloriesPerDayGoal;
    }

    public int getWorkoutsPerWeekGoal() {
        return workoutsPerWeekGoal;
    }

    public void setWorkoutsPerWeekGoal(int workoutsPerWeekGoal) {
        this.workoutsPerWeekGoal = workoutsPerWeekGoal;
    }

    public float getSleepPerNightGoal() {
        return sleepPerNightGoal;
    }

    public void setSleepPerNightGoal(float sleepPerNightGoal) {
        this.sleepPerNightGoal = sleepPerNightGoal;
    }

    public float getWeightGoal() {
        return weightGoal;
    }

    public void setWeightGoal(float weightGoal) {
        this.weightGoal = weightGoal;
    }
}
