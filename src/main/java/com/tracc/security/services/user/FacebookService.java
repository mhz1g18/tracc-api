package com.tracc.security.services.user;

import com.tracc.client.FacebookClient;
import com.tracc.models.user.FacebookUser;
import com.tracc.models.user.User;
import com.tracc.models.user.UserInfo;
import com.tracc.payload.response.JwtResponse;
import com.tracc.repository.user.UserRepository;
import com.tracc.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class FacebookService {

    private final FacebookClient fbClient;
    private final UserRepository userRepository;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public FacebookService(FacebookClient fbClient, UserRepository userRepository, UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.fbClient = fbClient;
        this.userRepository = userRepository;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public JwtResponse loginUser(String fbAccessToken, UserInfo userInfo) {

        FacebookUser fbUser = fbClient.getUser(fbAccessToken);
        String token;

        return userService.findById(fbUser.getId())
                .or(() -> Optional.ofNullable(userService.registerUser(convertTo(fbUser), userInfo)))
                .map(UserDetailsImpl::build)
                .map(userDetails -> new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()))
                .map(auth -> {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    return auth;
                })
                .map(auth -> {
                    String jwt = jwtUtils.generateJwtToken(auth);
                    UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
                    List<String> roles = userDetails.getAuthorities().stream()
                            .map(item -> item.getAuthority())
                            .collect(Collectors.toList());
                    return new JwtResponse(
                            jwt,
                            userDetails.getId(),
                            userDetails.getUsername(),
                            userDetails.getEmail(),
                            roles,
                            userDetails.getProfileId());
                })
                .orElseThrow(() ->
                        new IllegalStateException("unable to login facebook user id " + fbUser.getId()));

    }

    /**
     * Convert a user from the FB api to a User model
     * @param fbUser the facebook user
     * @return the converted user
     */

    private User convertTo(FacebookUser fbUser) {
        User user = new User(generateUsername(fbUser.getFirstName(), fbUser.getLastName()),
                            fbUser.getEmail(), generatePassword(10));
        user.setId(fbUser.getId());
        return user;
    }

    private String generateUsername(String firstName, String lastName) {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        return String.format("%s.%s.%06d", firstName, lastName, number);
    }

    private String generatePassword(int length) {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        char[] password = new char[length];

        password[0] = lowerCaseLetters  .charAt(random.nextInt(lowerCaseLetters.length()));
        password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
        password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        password[3] = numbers.charAt(random.nextInt(numbers.length()));

        for(int i = 4; i< length ; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return new String(password);
    }


}
