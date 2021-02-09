package com.bezkoder.spring.jwt.mongodb.controllers.auth;

import com.bezkoder.spring.jwt.mongodb.models.user.User;
import com.bezkoder.spring.jwt.mongodb.payload.request.FacebookLoginRequest;
import com.bezkoder.spring.jwt.mongodb.payload.request.LoginRequest;
import com.bezkoder.spring.jwt.mongodb.payload.request.SignupRequest;
import com.bezkoder.spring.jwt.mongodb.payload.response.MessageResponse;
import com.bezkoder.spring.jwt.mongodb.security.services.user.FacebookService;
import com.bezkoder.spring.jwt.mongodb.security.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
		return ResponseEntity.ok(userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword()));

	}

	@PostMapping("/facebook/signin")
	public ResponseEntity<?> facebookLogin(@Valid @RequestBody FacebookLoginRequest fbLoginRequest ){
		return ResponseEntity.ok(fbService.loginUser(fbLoginRequest.getAccessToken()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword());
		//return ResponseEntity.ok(userService.registerUser(signUpRequest));
		return ResponseEntity.ok(userService.registerUser(user));
	}
}

