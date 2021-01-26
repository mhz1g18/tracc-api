package com.bezkoder.spring.jwt.mongodb.models.diary;

import com.bezkoder.spring.jwt.mongodb.models.nutrition.Food;

import java.util.List;

public class FoodDiaryEntry extends DiaryEntry {

    private float calories;
    private float protein;
    private float carbs;
    private float fats;
    private float sugars;
    private float fiber;
    private float transfats;

    private List<Food> foodList;

    public FoodDiaryEntry() {
        super("ENTRY_FOOD");
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

        float calories = 0, fiber = 0, protein = 0, carbs = 0, fats = 0, sugars = 0, transfats = 0;
        float quantity;

        for(Food food : foodList) {

            quantity = food.getQuantity();

            calories += food.getCalories() * quantity;
            protein += food.getProtein()* quantity;
            carbs += food.getCarbs()* quantity;
            sugars += food.getSugars()* quantity;
            fiber += food.getFiber()* quantity;
            fats += food.getFats()* quantity;
            transfats += food.getTransfats()* quantity;
        }

        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
        this.sugars = sugars;
        this.transfats = transfats;
        this.fiber = fiber;

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
}
