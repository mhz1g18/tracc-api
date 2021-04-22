package com.tracc.controllers.user;

import com.tracc.models.user.UserInfo;
import com.tracc.security.services.userprofile.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@PreAuthorize("isAuthenticated()")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        return ResponseEntity.ok(userProfileService.findProfileByUserid());
    }

    @PostMapping("/profile")
    public ResponseEntity<?> setProfile(@RequestBody @Valid UserInfo userInfo) {
        return ResponseEntity.ok(userProfileService.setUserInfo(userInfo));
    }



}
