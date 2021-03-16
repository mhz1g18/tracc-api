package com.bezkoder.spring.jwt.mongodb.security.services.user;

import com.bezkoder.spring.jwt.mongodb.models.user.*;
import com.bezkoder.spring.jwt.mongodb.payload.request.SignupRequest;
import com.bezkoder.spring.jwt.mongodb.payload.response.JwtResponse;
import com.bezkoder.spring.jwt.mongodb.repository.user.RoleRepository;
import com.bezkoder.spring.jwt.mongodb.repository.user.UserRepository;
import com.bezkoder.spring.jwt.mongodb.security.jwt.JwtUtils;
import com.bezkoder.spring.jwt.mongodb.security.services.userprofile.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserProfileService userProfileService;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager, JwtUtils jwtUtils, PasswordEncoder passwordEncoder, UserProfileService userProfileService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.userProfileService = userProfileService;
    }

    public JwtResponse loginUser(String username, String password) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles,
                userDetails.getProfileId());
    }

    public User registerUser(User user, UserInfo userInfo) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalStateException("Username taken");
        }

        /*if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalStateException("Email taken");
        }*/

        if(userInfo == null) {
            userInfo = new UserInfo();
        }

        //Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error setting role")));

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User registered = userRepository.save(user);
        UserProfile userProfile = userProfileService.createNewProfile(registered.getId(), userInfo);

        registered.setProfileId(userProfile);
        return userRepository.save(registered);
    }


    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<User> findById(String id) { return userRepository.findById(id); }

    public User save(User user) {
        return userRepository.save(user);
    }


}
