package com.bezkoder.spring.jwt.mongodb.payload.request;

import com.bezkoder.spring.jwt.mongodb.models.user.UserInfo;

import javax.validation.constraints.NotBlank;

public class FacebookLoginRequest {

    @NotBlank
    private String accessToken;

    private UserInfo userInfo;

    public FacebookLoginRequest(String accessToken, UserInfo userInfo) {
        this.accessToken = accessToken;
        this.userInfo = userInfo;
    }

    public FacebookLoginRequest() {}

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
