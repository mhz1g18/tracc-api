package com.bezkoder.spring.jwt.mongodb.payload.request.diary;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = SleepDiaryEntryRequest.class, name = "ENTRY_SLEEP"),
        @JsonSubTypes.Type(value = NutritionDiaryEntryRequest.class, name = "ENTRY_NUTRITION")})
public abstract class DiaryEntryRequest {

    private String type;

    public DiaryEntryRequest() {

    }

    public DiaryEntryRequest(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
