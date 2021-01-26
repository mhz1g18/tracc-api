package com.bezkoder.spring.jwt.mongodb.exceptions.diary;

public class DiaryNotFoundException extends RuntimeException {

    public DiaryNotFoundException(String id) {
        super(id);
    }
}
