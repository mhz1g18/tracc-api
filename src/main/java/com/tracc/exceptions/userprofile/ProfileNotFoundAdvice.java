package com.tracc.exceptions.userprofile;

import com.tracc.exceptions.diary.DiaryNotFoundException;
import com.tracc.payload.response.MessageResponse;
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
