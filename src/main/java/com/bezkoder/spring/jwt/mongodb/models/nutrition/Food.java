package com.bezkoder.spring.jwt.mongodb.models.nutrition;


import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * Food Class
 * Extends Nutrition
 * Represents Nutrition documents in the MongoDB Nutrition collection
 * @see Nutrition
 *
 * fields include the common macronutrients and a list of micronutrients
 * where a micronutrient is represented by an instance of Supplement class
 *
 * Assumptions:
 * Nutritional values are per 100 GRAMS, calculated by
 *      the values, quantity and unit in a FoodRequest
 *
 */

public class Food extends Nutrition {

    @NotNull
    private float calories;
    private float protein;
    private float carbs;
    private float sugars;
    private float fiber;
    private float fats;
    private float transfats;

    private ArrayList<Supplement> micronutrients = new ArrayList<>();

    public Food() {
        super("FOOD");
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

    public float getCarbs() {
        return carbs;
    }

    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    public float getSugars() {
        return sugars;
    }

    public void setSugars(float sugars) {
        this.sugars = sugars;
    }

    public float getFats() {
        return fats;
    }

    public void setFats(float fats) {
        this.fats = fats;
    }

    public float getTransfats() {
        return transfats;
    }

    public void setTransfats(float transfats) {
        this.transfats = transfats;
    }

    public float getFiber() {
        return fiber;
    }

    public void setFiber(float fiber) {
        this.fiber = fiber;
    }


    public ArrayList<Supplement> getMicronutrients() {
        return micronutrients;
    }

    public void setMicronutrients(ArrayList<Supplement> micronutrients) {
        this.micronutrients = micronutrients;
    }
}
