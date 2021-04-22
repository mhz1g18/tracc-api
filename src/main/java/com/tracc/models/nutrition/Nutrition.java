package com.tracc.models.nutrition;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Base Abstract Model for documents
 * in the Nutrition MongoDB Collection
 *
 * @Document annotation specifies the collection name
 * @JsonTypeInfo and @JSonSubTypes annotations
 * configure the Jackson object mapper to map objects
 * with property with fields type equal to string "FOOD" to
 * the Food class that extends the nutrition class and similarly
 * type equals to "SUPPLEMENT" are mapped to the supplement class
 * that also extends Nutrition
 *
 * Includes common fields for classes that extend this one
 * Also includes the relevant getters and setters
 *
 * @see Supplement
 * @see Nutrition
 */

@Document(collection = "nutrition")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = Food.class, name = "FOOD"),
               @JsonSubTypes.Type(value = Supplement.class, name="SUPPLEMENT")})
//@CompoundIndex(def = "{ 'createdBy' : 1, 'id' : 1 }")
abstract public class Nutrition {

    @Id
    private String id;

    //@Indexed
    private String type;

    @NotBlank
    private String name;

    private String description;

    private int quantity = 100;

    private EUnit unit = EUnit.UNIT_G;

    //@Indexed
    private String createdBy;

    private List<String> categories = new ArrayList<>();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt = new Date();

    public Nutrition(String type) {
        this.type = type;
    }

    public Nutrition() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    @Override
    public String toString() {
        return this.id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public EUnit getUnit() {
        return unit;
    }

    public void setUnit(EUnit unit) {
        this.unit = unit;
    }
}
