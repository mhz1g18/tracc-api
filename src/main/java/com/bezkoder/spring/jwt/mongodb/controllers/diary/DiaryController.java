package com.bezkoder.spring.jwt.mongodb.controllers.diary;

import com.bezkoder.spring.jwt.mongodb.exceptions.diary.DiaryEntryNotFoundException;
import com.bezkoder.spring.jwt.mongodb.models.diary.Diary;
import com.bezkoder.spring.jwt.mongodb.models.diary.DiaryEntry;
import com.bezkoder.spring.jwt.mongodb.security.services.diary.DiaryService;
import com.bezkoder.spring.jwt.mongodb.security.services.userprofile.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@PreAuthorize("isAuthenticated()")
public class DiaryController {

	private final DiaryService diaryService;
	private final UserProfileService userProfileService;

	@Autowired
	public DiaryController(DiaryService diaryService,
						   UserProfileService userProfileService) {
		this.diaryService = diaryService;
		this.userProfileService = userProfileService;
	}

	@GetMapping("/diary")
	public ResponseEntity<?> getUserDiary() {

		Diary diary = userProfileService.getUserDiary();
		return ResponseEntity.ok(diary);
	}

	@PostMapping("/diary/entries")
	public ResponseEntity<?> addDiaryEntry(@Valid @RequestBody DiaryEntry entry) {

		Diary diary = userProfileService.getUserDiary();
		diaryService.addEntry(diary, entry);
		return ResponseEntity.ok(entry);
	}

	@DeleteMapping("/diary/entries/{id}")
	public ResponseEntity<?> removeDiaryEntry(@PathVariable String id) {

		Diary diary = userProfileService.getUserDiary();
		diaryService.removeEntry(diary, id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/diary/entries/{id}")
	public ResponseEntity<?> getDiaryEntry(@PathVariable String id) {

		Diary diary = userProfileService.getUserDiary();
		DiaryEntry entry = diaryService.getEntry(diary, id)
								       .orElseThrow(() ->new DiaryEntryNotFoundException(id));

		return ResponseEntity.ok(entry);
	}

	@PutMapping("diary/entries/{id}")
	public ResponseEntity<?> editDiaryEntry(@PathVariable String id,
											@Valid @RequestBody DiaryEntry newEntry) {

		Diary diary = userProfileService.getUserDiary();
		Optional<DiaryEntry> temp = diaryService.getEntry(diary, id);

		if ( temp.isPresent() ) {
			DiaryEntry entryToEdit = temp.get();

			return ResponseEntity.ok(diaryService.editEntry(diary, entryToEdit, newEntry));
		}

		diaryService.addEntry(diary, newEntry);
		return ResponseEntity.ok(newEntry);
	}



}
