package com.tracc.exceptions.nutrition;

public class NutritionNotFoundException extends RuntimeException{
    public NutritionNotFoundException(String id) {
        super(id);
    }
}
