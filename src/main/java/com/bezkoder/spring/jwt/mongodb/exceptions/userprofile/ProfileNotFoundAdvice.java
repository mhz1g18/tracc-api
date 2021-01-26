package com.bezkoder.spring.jwt.mongodb.exceptions.userprofile;

import com.bezkoder.spring.jwt.mongodb.exceptions.diary.DiaryNotFoundException;
import com.bezkoder.spring.jwt.mongodb.payload.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProfileNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ProfileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    MessageResponse diaryNotFoundHandler(DiaryNotFoundException ex) {
        return new MessageResponse("error", "No profile with id of" + ex.getMessage());
    }
}
