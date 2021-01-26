package com.bezkoder.spring.jwt.mongodb.security.services.userprofile;

import com.bezkoder.spring.jwt.mongodb.exceptions.userprofile.ProfileNotFoundException;
import com.bezkoder.spring.jwt.mongodb.models.diary.Diary;
import com.bezkoder.spring.jwt.mongodb.models.user.UserProfile;
import com.bezkoder.spring.jwt.mongodb.repository.diary.DiaryRepository;
import com.bezkoder.spring.jwt.mongodb.repository.userprofile.UserProfileRepository;
import com.bezkoder.spring.jwt.mongodb.security.jwt.UserProfileUtils;
import com.bezkoder.spring.jwt.mongodb.security.services.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final DiaryRepository diaryRepository;
    private final UserProfileUtils userProfileUtils;

    @Autowired
    public UserProfileService(@Qualifier("MongoRepo") UserProfileRepository userProfileRepository,
                              @Qualifier("MongoRepo") DiaryRepository diaryRepository,
                              UserProfileUtils userProfileUtils) {
        this.userProfileRepository = userProfileRepository;
        this.diaryRepository = diaryRepository;
        this.userProfileUtils = userProfileUtils;
    }

    public UserProfile createNewProfile() {
        Diary userDiary = diaryRepository.save(new Diary());
        UserProfile userProfile = new UserProfile(userDiary);
        return userProfileRepository.save(userProfile);
    }

    public Optional<UserProfile> findProfileById(String id) {
        return userProfileRepository.findById(id);
    }

    public Diary getUserDiary() {
        UserDetailsImpl userDetails = userProfileUtils.getUserDetails();

        UserProfile userProfile = this.findProfileById(userDetails.getProfileId().getId())
                .orElseThrow(() -> new ProfileNotFoundException(userDetails.getProfileId().getId()));

        return userProfile.getDiaryId();
    }

}
