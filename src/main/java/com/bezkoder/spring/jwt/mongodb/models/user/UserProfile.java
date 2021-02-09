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

    private String userId;

    private String diary_id;

    private UserInfo userInfo;

    /* @DBRef
    private List<Nutrition> custom_nutrition = new ArrayList<>();*/

    public UserProfile(String userId, String diary_id, UserInfo userInfo) {
        this.userId = userId;
        this.diary_id = diary_id;
        this.userInfo = userInfo;
    }

    public UserProfile(String userId, String diary_id) {
        this.userId = userId;
        this.diary_id = diary_id;
        this.userInfo = new UserInfo();
    }

    public UserProfile() {}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiaryId() {
        return diary_id;
    }

    public void setDiaryId(String diary_id) {
        this.diary_id = diary_id;
    }

/*    public List<Nutrition> getCustomNutrition() {
        return custom_nutrition;
    }

    public void setCustomNutrition(List<Nutrition> custom_nutrition) {
        this.custom_nutrition = custom_nutrition;
    }*/

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
