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
    private int calories;
    private int protein;
    private int carbs;
    private int sugars;
    private int fiber;
    private int fats;
    private int transfats;

    private ArrayList<Supplement> micronutrients = new ArrayList<>();

    public Food() {
        super("FOOD");
    }

    public @NotNull int getCalories() {
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

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public int getSugars() {
        return sugars;
    }

    public void setSugars(int sugars) {
        this.sugars = sugars;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public int getTransfats() {
        return transfats;
    }

    public void setTransfats(int transfats) {
        this.transfats = transfats;
    }

    public int getFiber() {
        return fiber;
    }

    public void setFiber(int fiber) {
        this.fiber = fiber;
    }


    public ArrayList<Supplement> getMicronutrients() {
        return micronutrients;
    }

    public void setMicronutrients(ArrayList<Supplement> micronutrients) {
        this.micronutrients = micronutrients;
    }
}
