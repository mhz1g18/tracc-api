package com.bezkoder.spring.jwt.mongodb.payload.request.nutrition;

/**
 * Extends NutritionRequest
 *
 * Used to construct Supplement objects after parsing
 * request bodies' JSON and mapping it to a SupplementRequest object
 */

public class SupplementRequest  extends NutritionRequest{

    public SupplementRequest() {
        super("SUPPLEMENT");
    }


}
