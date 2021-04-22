package com.tracc.exceptions.workouts;

import com.tracc.payload.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExerciseNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ExerciseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    MessageResponse exerciseNotFoundHandler(ExerciseNotFoundException ex) {
        return new MessageResponse("error", "No exercise with id of" + ex.getMessage());
    }
}
