package com.bezkoder.spring.jwt.mongodb.exceptions.nutrition;


public class NutritionNotFoundException extends RuntimeException{
    public NutritionNotFoundException(String id) {
        super(id);
    }
}
