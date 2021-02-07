package com.bezkoder.spring.jwt.mongodb.payload.request.nutrition;

import java.util.HashMap;
import java.util.Map;

/**
 * Extends NutritionRequest
 *
 * Used to construct Food objects after parsing
 * request bodies' JSON and mapping it to a FoodRequest object
 */

public class FoodRequest extends  NutritionRequest{
    private float calories;
    private float protein;
    private float carbohydrates;
    private float fats;
    private float fiber;
    private float sugars;
    private float transFats;

    private Map<String, Integer> micronutrientIds = new HashMap<>();
    private int quantity;

    public FoodRequest() {
        super("FOOD");
    }

    public FoodRequest(int calories, int protein, int carbohydrates, int fats, int fiber, int sugars, int transFats,
                       int quantity, HashMap<String, Integer> micronutrientIds) {
        super("FOOD");
        this.calories = calories;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
        this.fiber = fiber;
        this.sugars = sugars;
        this.transFats = transFats;
        this.micronutrientIds = micronutrientIds;
    }


    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(float carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public float getFats() {
        return fats;
    }

    public void setFats(float fats) {
        this.fats = fats;
    }

    public float getFiber() {
        return fiber;
    }

    public void setFiber(float fiber) {
        this.fiber = fiber;
    }

    public float getSugars() {
        return sugars;
    }

    public void setSugars(float sugars) {
        this.sugars = sugars;
    }

    public float getTransFats() {
        return transFats;
    }

    public void setTransFats(float transFats) {
        this.transFats = transFats;
    }

    public Map<String, Integer> getMicronutrientIds() {
        return micronutrientIds;
    }

    public void setMicronutrientIds(Map<String, Integer> micronutrientIds) {
        this.micronutrientIds = micronutrientIds;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
