package com.tracc.models.diary;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Document(collection = "diaries")
public class Diary {

    @Id
    private String id;

    private String userId;

    private Map<LocalDate, DiaryEntryList> entries = new HashMap<>();

    public Diary(String userid) {
        this.userId = userid;
    }

    public Diary() {

    }

    public Map<LocalDate, DiaryEntryList> getEntries() {
        return entries;
    }

    public void setEntries(Map<LocalDate, DiaryEntryList> entries) {
        this.entries = entries;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

