package com.tracc.models.diary;

import com.tracc.models.nutrition.Food;
import com.tracc.models.nutrition.Supplement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NutritionDiaryEntry extends DiaryEntry {

    private float calories;
    private float protein;
    private float carbs;
    private float fats;
    private float sugars;
    private float fiber;
    private float transfats;

    private List<Food> foodList = new ArrayList<>();
    private List<Supplement> supplementList = new ArrayList<>();

    private Set<Supplement> micronutrients = new HashSet<>();

    public NutritionDiaryEntry() {
        super("ENTRY_NUTRITION");
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getCarbs() {
        return carbs;
    }

    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    public float getFats() {
        return fats;
    }

    public void setFats(float fats) {
        this.fats = fats;
    }

    public float getSugars() {
        return sugars;
    }

    public void setSugars(float sugars) {
        this.sugars = sugars;
    }

    public float getFiber() {
        return fiber;
    }

    public void setFiber(float fiber) {
        this.fiber = fiber;
    }

    public float getTransfats() {
        return transfats;
    }

    public void setTransfats(float transfats) {
        this.transfats = transfats;
    }

    public List<Supplement> getSupplementList() {
        return supplementList;
    }

    public void setSupplementList(List<Supplement> supplementList) {
        this.supplementList = supplementList;
    }

    public Set<Supplement> getMicronutrients() {
        return micronutrients;
    }

    public void setMicronutrients(Set<Supplement> micronutrients) {
        this.micronutrients = micronutrients;
    }
}
