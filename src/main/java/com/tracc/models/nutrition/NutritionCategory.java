package com.tracc.models.nutrition;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "nutrition_category")
public class NutritionCategory {

    @Id
    private String id;

    @Indexed
    private String name;

    /**
     * List of all nutrition that is in the category
     * Uses reference over embedding to ensure consistency
     */

    @DBRef
    private List<Nutrition> nutritionList = new ArrayList<>();

    public NutritionCategory() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public List<Nutrition> getNutritionList() {
        return nutritionList;
    }

    public void setNutritionList(List<Nutrition> nutritionList) {
        this.nutritionList = nutritionList;
    }


}
