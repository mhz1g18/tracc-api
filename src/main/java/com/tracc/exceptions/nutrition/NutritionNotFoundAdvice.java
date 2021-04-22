package com.tracc.exceptions.nutrition;

import com.tracc.payload.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class NutritionNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(NutritionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    MessageResponse nutritionNotFoundHandler(NutritionNotFoundException ex) {
        return new MessageResponse("error", "No nutrition with id of" + ex.getMessage());
    }
}

