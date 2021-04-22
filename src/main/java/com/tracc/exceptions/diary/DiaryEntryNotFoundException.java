package com.tracc.exceptions.diary;

public class DiaryEntryNotFoundException extends RuntimeException {

    public DiaryEntryNotFoundException(String id) {
        super(id);
    }
}
