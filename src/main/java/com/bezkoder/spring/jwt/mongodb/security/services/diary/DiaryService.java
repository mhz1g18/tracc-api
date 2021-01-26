package com.bezkoder.spring.jwt.mongodb.security.services.diary;

import com.bezkoder.spring.jwt.mongodb.exceptions.diary.DiaryEntryNotFoundException;
import com.bezkoder.spring.jwt.mongodb.models.diary.Diary;
import com.bezkoder.spring.jwt.mongodb.models.diary.DiaryEntry;
import com.bezkoder.spring.jwt.mongodb.models.diary.SleepDiaryEntry;
import com.bezkoder.spring.jwt.mongodb.repository.diary.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;

    @Autowired
    public DiaryService(@Qualifier("MongoRepo") DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public Diary createNew() {
        return diaryRepository.save(new Diary());
    }

    public Optional<Diary> findById(String id) {
        return diaryRepository.findById(id);
    }

    public Diary addEntry(Diary diary, DiaryEntry entry) {
        List<DiaryEntry> diaryEntries = diary.getEntries();
        diaryEntries.add(entry);
        return diaryRepository.save(diary);
    }

    public Optional<DiaryEntry> getEntry(Diary diary, String entryId) {

        return diary.getEntries()
                    .stream()
                    .filter(entry -> entry.getId().equals(entryId))
                    .findFirst();
    }


    public void removeEntry(Diary diary, String entryId) {
        List<DiaryEntry> diaryEntries = diary.getEntries();
        DiaryEntry entryToRemove = diaryEntries.stream()
                                               .filter(entry -> entry.getId().equals(entryId))
                                               .findFirst()
                                               .orElseThrow(() -> new DiaryEntryNotFoundException(entryId));

        diaryEntries.remove(entryToRemove);
        diaryRepository.save(diary);
    }

    public DiaryEntry editEntry(Diary diary, DiaryEntry oldEntry, DiaryEntry newEntry) {

        if(oldEntry instanceof SleepDiaryEntry && newEntry instanceof  SleepDiaryEntry) {
            ((SleepDiaryEntry) oldEntry).setDuration(((SleepDiaryEntry) newEntry).getDuration());
            ((SleepDiaryEntry) oldEntry).setNotes(((SleepDiaryEntry) newEntry).getNotes());
            diaryRepository.save(diary);
            return oldEntry;
        } else {
            throw new DiaryEntryNotFoundException("Entry type does not match");
        }

    }

}
