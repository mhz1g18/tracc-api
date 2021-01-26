package com.bezkoder.spring.jwt.mongodb.models.diary;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.*;
import org.bson.types.ObjectId;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = SleepDiaryEntry.class, name = "ENTRY_SLEEP"),
               @JsonSubTypes.Type(value = FoodDiaryEntry.class, name = "ENTRY_FOOD")})
public abstract class DiaryEntry {

    private ObjectId id = new ObjectId();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date timestamp = new Date();

    private String type;

    public DiaryEntry(String type) {
        this.type = type;
    }

    public DiaryEntry() {}

    public String getId() {
        return id.toString();
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
