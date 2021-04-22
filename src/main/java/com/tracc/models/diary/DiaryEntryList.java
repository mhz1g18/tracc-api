package com.tracc.models.diary;

import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

public class DiaryEntryList {
    @DBRef
    public List<DiaryEntry> diaryEntries = new ArrayList<>();

}
