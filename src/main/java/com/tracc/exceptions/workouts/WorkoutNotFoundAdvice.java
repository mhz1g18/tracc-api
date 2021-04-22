package com.tracc.exceptions.workouts;

import com.tracc.payload.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class WorkoutNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(WorkoutNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    MessageResponse workoutNotFoundHandler(WorkoutNotFoundException ex) {
        return new MessageResponse("error", "No workout with id of" + ex.getMessage());
    }
}
