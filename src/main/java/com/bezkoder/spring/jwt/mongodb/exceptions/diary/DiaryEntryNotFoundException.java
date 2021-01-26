package com.bezkoder.spring.jwt.mongodb.exceptions.diary;

public class DiaryEntryNotFoundException extends RuntimeException {

    public DiaryEntryNotFoundException(String id) {
        super(id);
    }
}
