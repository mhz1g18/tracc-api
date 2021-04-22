package com.tracc.payload.request.nutrition;

import com.tracc.models.nutrition.EUnit;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents the abstract NutritionRequest
 * Incoming JSON Payloads in request bodies
 * are parsed to FoodRequest and SupplementRequest
 * classes that extend this abstract one. Payloads
 * with type fields = "FOOD" are mapped to FoodRequest class
 * and fields = "SUPPLEMENT" are mapped to SupplementRequest class
 *
 * Those classes include the fields that represent what is
 * expected in a body of the request when the mapping method
 * has a parameter of NutritionRequest defined. Then the nutrition
 * service uses those payload objects to construct the actual models
 * used in the database.
 *
 * @see FoodRequest
 * @see SupplementRequest
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = FoodRequest.class, name = "FOOD"),
        @JsonSubTypes.Type(value = SupplementRequest.class, name="SUPPLEMENT")})
public abstract class NutritionRequest {

    private String type;
    @NotBlank
    private String name;
    private List<String> categories = new ArrayList<>();
    private String description;
    private EUnit unit;

    public NutritionRequest(String type) {
        this.type = type;
    }

    public NutritionRequest() {}

    public NutritionRequest(String type, String name, ArrayList<String> categories, String description, EUnit unit) {
        this.type = type;
        this.name = name;
        this.categories = categories;
        this.description = description;
        this.unit = unit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EUnit getUnit() {
        return unit;
    }

    public void setUnit(EUnit unit) {
        this.unit = unit;
    }
}
