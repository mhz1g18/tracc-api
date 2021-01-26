package com.bezkoder.spring.jwt.mongodb.models.user;

import com.bezkoder.spring.jwt.mongodb.models.diary.Diary;
import com.bezkoder.spring.jwt.mongodb.models.nutrition.Nutrition;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "user_profiles")
public class UserProfile {
    @Id
    private String id;

    @DBRef
    private Diary diary_id;

    @DBRef
    private List<Nutrition> custom_nutrition = new ArrayList<>();

    public UserProfile(Diary diary_id) {
        this.diary_id = diary_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Diary getDiaryId() {
        return diary_id;
    }

    public void setDiaryId(Diary diary_id) {
        this.diary_id = diary_id;
    }

    public List<Nutrition> getCustomNutrition() {
        return custom_nutrition;
    }

    public void setCustomNutrition(List<Nutrition> custom_nutrition) {
        this.custom_nutrition = custom_nutrition;
    }
}
