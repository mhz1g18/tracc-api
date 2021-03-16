package com.bezkoder.spring.jwt.mongodb.controllers.auth;

import com.bezkoder.spring.jwt.mongodb.models.user.User;
import com.bezkoder.spring.jwt.mongodb.payload.request.FacebookLoginRequest;
import com.bezkoder.spring.jwt.mongodb.payload.request.LoginRequest;
import com.bezkoder.spring.jwt.mongodb.payload.request.SignupRequest;
import com.bezkoder.spring.jwt.mongodb.payload.response.JwtResponse;
import com.bezkoder.spring.jwt.mongodb.payload.response.MessageResponse;
import com.bezkoder.spring.jwt.mongodb.security.services.user.FacebookService;
import com.bezkoder.spring.jwt.mongodb.security.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UserService userService;
	private final FacebookService fbService;

	@Autowired
	public AuthController(UserService userService, FacebookService fbService) {
		this.userService = userService;
		this.fbService = fbService;
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		try {
			JwtResponse response = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
			return ResponseEntity.ok(response);
		} catch (AuthenticationException e) {
			return ResponseEntity.ok(new MessageResponse("Error", e.getMessage()));
		}
	}

	@PostMapping("/facebook/signin")
	public ResponseEntity<?> facebookLogin(@Valid @RequestBody FacebookLoginRequest fbLoginRequest){
		return ResponseEntity.ok(fbService.loginUser(fbLoginRequest.getAccessToken(), fbLoginRequest.getUserInfo()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		/*User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword());
		return ResponseEntity.ok(userService.registerUser(user, signUpRequest.getUserInfo()));*/
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword());
		userService.registerUser(user, signUpRequest.getUserInfo());
		JwtResponse response = userService.loginUser(signUpRequest.getUsername(), signUpRequest.getPassword());
		return ResponseEntity.ok(response);
	}
}

