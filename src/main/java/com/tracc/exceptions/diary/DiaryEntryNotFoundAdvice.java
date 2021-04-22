package com.tracc.exceptions.diary;

import com.tracc.payload.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DiaryEntryNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(DiaryEntryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    MessageResponse diaryNotFoundHandler(DiaryEntryNotFoundException ex) {
        return new MessageResponse("error", "No diary entry with id of" + ex.getMessage());
    }
}
