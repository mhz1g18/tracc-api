package com.tracc.exceptions.diary;

public class DiaryNotFoundException extends RuntimeException {

    public DiaryNotFoundException(String id) {
        super(id);
    }
}
