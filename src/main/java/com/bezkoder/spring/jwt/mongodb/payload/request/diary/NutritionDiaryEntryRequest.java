package com.bezkoder.spring.jwt.mongodb.payload.request.diary;

import com.bezkoder.spring.jwt.mongodb.models.nutrition.Food;
import com.bezkoder.spring.jwt.mongodb.models.nutrition.Supplement;

import java.util.List;

public class NutritionDiaryEntryRequest extends DiaryEntryRequest {

    private List<Food> foodList;
    private List<Supplement> supplementList;
    private boolean calculatedValues;

    public NutritionDiaryEntryRequest() {
        super("ENTRY_NUTRITION");
    }


    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    public List<Supplement> getSupplementList() {
        return supplementList;
    }

    public void setSupplementList(List<Supplement> supplementList) {
        this.supplementList = supplementList;
    }

    public boolean isCalculatedValues() {
        return calculatedValues;
    }

    public void setCalculatedValues(boolean calculatedValues) {
        this.calculatedValues = calculatedValues;
    }
}
