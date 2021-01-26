package com.bezkoder.spring.jwt.mongodb.models.diary;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "diaries")
public class Diary {

    @Id
    private String id;

    private List<DiaryEntry> entries = new ArrayList<>();

    public Diary() {

    }

    public List<DiaryEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<DiaryEntry> entries) {
        this.entries = entries;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
