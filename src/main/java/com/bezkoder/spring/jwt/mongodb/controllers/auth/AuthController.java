package com.bezkoder.spring.jwt.mongodb.controllers.auth;

import com.bezkoder.spring.jwt.mongodb.models.user.ERole;
import com.bezkoder.spring.jwt.mongodb.models.user.Role;
import com.bezkoder.spring.jwt.mongodb.models.user.User;
import com.bezkoder.spring.jwt.mongodb.models.user.UserProfile;
import com.bezkoder.spring.jwt.mongodb.payload.request.LoginRequest;
import com.bezkoder.spring.jwt.mongodb.payload.request.SignupRequest;
import com.bezkoder.spring.jwt.mongodb.payload.response.JwtResponse;
import com.bezkoder.spring.jwt.mongodb.payload.response.MessageResponse;
import com.bezkoder.spring.jwt.mongodb.security.jwt.JwtUtils;
import com.bezkoder.spring.jwt.mongodb.security.services.user.RoleService;
import com.bezkoder.spring.jwt.mongodb.security.services.user.UserDetailsImpl;
import com.bezkoder.spring.jwt.mongodb.security.services.user.UserService;
import com.bezkoder.spring.jwt.mongodb.security.services.userprofile.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UserService userService;
	private final RoleService roleService;
	private final UserProfileService userProfileService;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder encoder;
	private  final JwtUtils jwtUtils;

	@Autowired
	public AuthController(UserService userService,
						  RoleService roleService,
						  UserProfileService userProfileService,
						  AuthenticationManager authenticationManager,
						  PasswordEncoder encoder,
						  JwtUtils jwtUtils) {
		this.userService = userService;
		this.roleService = roleService;
		this.userProfileService = userProfileService;
		this.authenticationManager = authenticationManager;
		this.encoder = encoder;
		this.jwtUtils = jwtUtils;
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles,
												 userDetails.getProfileId()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userService.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("error", "Username taken"));
		}

		if (userService.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("error", "Email taken"));
		}



		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleService.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleService.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleService.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleService.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		// Create new user's account
		UserProfile userProfile = userProfileService.createNewProfile();
		User user = new User(signUpRequest.getUsername(),
				signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()),
				userProfile);

		user.setRoles(roles);
		userService.save(user);

		return ResponseEntity.ok(new MessageResponse("success", "User registered"));
	}
}
