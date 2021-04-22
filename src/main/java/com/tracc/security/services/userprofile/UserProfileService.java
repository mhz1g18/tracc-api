package com.tracc.security.services.userprofile;

import com.tracc.exceptions.userprofile.ProfileNotFoundException;
import com.tracc.models.diary.Diary;
import com.tracc.models.user.UserGoals;
import com.tracc.models.user.UserInfo;
import com.tracc.models.user.UserProfile;
import com.tracc.repository.diary.DiaryRepository;
import com.tracc.repository.userprofile.UserProfileRepository;
import com.tracc.security.jwt.UserProfileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class  UserProfileService {

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

    public UserProfile createNewProfile(String userId, UserInfo userInfo) {
        Diary userDiary = diaryRepository.save(new Diary(userId));
        UserProfile userProfile = new UserProfile(userId, userDiary.getId(), userInfo);
        if(userProfile.getUserInfo().getUserGoals() == null) {
            userProfile.getUserInfo().setUserGoals(new UserGoals());
        }
        return userProfileRepository.save(userProfile);
    }

    public UserProfile findProfileById(String id) {
        return userProfileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));
    }

    public UserProfile findProfileByUserid() {
        String id = userProfileUtils.getUserDetails().getId();
        return userProfileRepository.findByUserId(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));
    }

    public UserProfile setUserInfo(UserInfo userInfo) {
        UserProfile profile = findProfileByUserid();
        profile.setUserInfo(userInfo);
        return userProfileRepository.save(profile);
    }
}
