package com.tracc.exceptions.diary;

import com.tracc.payload.response.MessageResponse;
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

