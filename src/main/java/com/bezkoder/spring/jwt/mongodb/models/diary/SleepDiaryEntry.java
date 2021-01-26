package com.bezkoder.spring.jwt.mongodb.models.diary;

import javax.validation.constraints.NotNull;

public class SleepDiaryEntry extends DiaryEntry {

    @NotNull
    private float duration;
    private String notes;

    public SleepDiaryEntry(float duration, String notes) {
        super("ENTRY_SLEEP");
        this.duration = duration;
        this.notes = notes;
    }

    public SleepDiaryEntry() {
        super("ENTRY_SLEEP");
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
