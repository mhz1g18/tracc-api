package com.bezkoder.spring.jwt.mongodb.models.nutrition;


import javax.validation.constraints.NotNull;

public class Food extends Nutrition {

    @NotNull
    private float calories;
    private float protein;
    private float carbs;
    private float sugars;
    private float fiber;
    private float fats;
    private float transfats;

    private float quantity = 1F;

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

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
}
