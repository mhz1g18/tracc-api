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
    private int calories;
    private int protein;
    private int carbohydrates;
    private int fats;
    private int fiber;
    private int sugars;
    private int transFats;

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


    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public int getFiber() {
        return fiber;
    }

    public void setFiber(int fiber) {
        this.fiber = fiber;
    }

    public int getSugars() {
        return sugars;
    }

    public void setSugars(int sugars) {
        this.sugars = sugars;
    }

    public int getTransFats() {
        return transFats;
    }

    public void setTransFats(int transFats) {
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
