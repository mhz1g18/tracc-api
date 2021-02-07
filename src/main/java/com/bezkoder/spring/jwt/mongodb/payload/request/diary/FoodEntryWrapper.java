package com.bezkoder.spring.jwt.mongodb.payload.request.diary;

import com.bezkoder.spring.jwt.mongodb.models.nutrition.EUnit;
import com.bezkoder.spring.jwt.mongodb.models.nutrition.Food;

/**
 * FoodEntryWrapper class
 * Used to wrap food items in NutritionDiaryEntryRequests
 * Food entities have their macros/micros calculated per 100 grams upon being added to the database
 * To not be confusing,
 */
public class FoodEntryWrapper {

    private Food food;
    private float quantity;
    private EUnit unit;

    public FoodEntryWrapper () {}


    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public EUnit getUnit() {
        return unit;
    }

    public void setUnit(EUnit unit) {
        this.unit = unit;
    }
}
