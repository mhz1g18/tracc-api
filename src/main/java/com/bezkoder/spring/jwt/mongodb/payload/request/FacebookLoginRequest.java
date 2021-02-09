package com.bezkoder.spring.jwt.mongodb.payload.request;

import javax.validation.constraints.NotBlank;

public class FacebookLoginRequest {

    @NotBlank
    private String accessToken;

    public FacebookLoginRequest(String accessToken) {
        this.accessToken = accessToken;
    }

    public FacebookLoginRequest() {}

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
