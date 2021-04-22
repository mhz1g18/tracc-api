package com.tracc.payload.request.diary;


public class SleepDiaryEntryRequest extends DiaryEntryRequest{

    private float duration;
    private String notes;

    public SleepDiaryEntryRequest() {
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
