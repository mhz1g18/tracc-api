package com.bezkoder.spring.jwt.mongodb.payload.response;

import com.bezkoder.spring.jwt.mongodb.models.user.UserProfile;

import java.util.List;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String id;
	private String username;
	private String email;
	private List<String> roles;
	private UserProfile profile_id;

	public JwtResponse(String accessToken, String id, String username, String email, List<String> roles, UserProfile profile_id) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.profile_id = profile_id;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public UserProfile getProfileId() {
		return profile_id;
	}

	public void setProfileId(UserProfile diaryId) {
		this.profile_id = profile_id;
	}
}
