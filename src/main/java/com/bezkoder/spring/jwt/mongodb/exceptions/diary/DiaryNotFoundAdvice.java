package com.bezkoder.spring.jwt.mongodb.exceptions.diary;

import com.bezkoder.spring.jwt.mongodb.payload.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class DiaryNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(DiaryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    MessageResponse diaryNotFoundHandler(DiaryNotFoundException ex) {
        return new MessageResponse("error", "No diary diary with id of" + ex.getMessage());
    }
}

