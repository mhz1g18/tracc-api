package com.tracc.models.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_profiles")
public class UserProfile {
    @Id
    private String id;

    private String userId;

    private String diaryId;

    private UserInfo userInfo;

    public UserProfile(String userId, String diaryId, UserInfo userInfo) {
        this.userId = userId;
        this.diaryId = diaryId;
        this.userInfo = userInfo;
    }

    public UserProfile(String userId, String diary_id) {
        this.userId = userId;
        this.diaryId = diary_id;
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
        return diaryId;
    }

    public void setDiaryId(String diary_id) {
        this.diaryId = diary_id;
    }

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
