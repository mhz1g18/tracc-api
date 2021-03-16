package com.bezkoder.spring.jwt.mongodb.controllers.diary;

import com.bezkoder.spring.jwt.mongodb.models.diary.Diary;
import com.bezkoder.spring.jwt.mongodb.models.diary.DiaryEntry;
import com.bezkoder.spring.jwt.mongodb.models.diary.DiaryEntryList;
import com.bezkoder.spring.jwt.mongodb.models.diary.NutritionDiaryEntry;
import com.bezkoder.spring.jwt.mongodb.models.nutrition.Food;
import com.bezkoder.spring.jwt.mongodb.models.nutrition.Nutrition;
import com.bezkoder.spring.jwt.mongodb.models.nutrition.Supplement;
import com.bezkoder.spring.jwt.mongodb.payload.request.diary.DiaryEntryRequest;
import com.bezkoder.spring.jwt.mongodb.security.services.diary.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * DiaryController
 *
 * Handles URI mappings and building appropriate responses
 * for CRUD operations and queries
 * related to documents in the diaries and diary_entries collections
 *
 * The actual logic of those operations
 * is performed in the DiaryService
 * @see DiaryService
 *
 * Annotated with @PreAuthorize("isAuthenticated()")
 * to ensure only authenticated users may access the resources
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@PreAuthorize("isAuthenticated()")
public class DiaryController {

	private final DiaryService diaryService;

	@Autowired
	public DiaryController(DiaryService diaryService) {
		this.diaryService = diaryService;
	}

	/**
	 * Retrieve the diary of the User requesting it
	 *
	 * @return the Diary
	 */

	@GetMapping("/diary")
	public ResponseEntity<?> getUserDiary() {

		Diary diary = diaryService.findByUserId();
		return ResponseEntity.ok(diary);
	}

	/**
	 * Retrieve the *only* the map in the user's Diary
	 * that maps date values to entries made in that date
	 *
	 * @return the Diary
	 */

	@GetMapping("/diary/entries")
	public ResponseEntity<?> getUserDiaryEntries() {

		Diary diary = diaryService.findByUserId();
		return ResponseEntity.ok(diary.getEntries());
	}

	@GetMapping(value = "/diary/entries", params = "type")
	public ResponseEntity<?> findByDate(String type) {

		return ResponseEntity.ok(diaryService.getEntriesByType(type));

	}

	/**
	 * Retrieve a list of entries for a specific date
	 * @param date the specified date
	 * @return list of results
	 */

	@GetMapping(value = "/diary/entries", params = "date")
	public ResponseEntity<?> findByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		return ResponseEntity.ok(diaryService.getDiaryEntriesByDate(date));
	}

	/**
	 * Retrieve a specific Diary Entry in the user's diary
	 * @param id the id of the entry
	 *
	 * @return the Diary Entry
	 */

	@GetMapping("/diary/entries/{id}")
	public ResponseEntity<?> getEntryById(@PathVariable String id) {
		return ResponseEntity.ok(diaryService.getDiaryEntryById(id));
	}

	/**
	 * Add a diary entry document to the diary entries collection
	 * And add reference to it in the user's diary entries list
	 * @param entry the DiaryEntryRequest object that the incoming request
	 *              is mapped to
	 *
	 * @return the persisted DiaryEntry
	 */

	@PostMapping("/diary/entries")
	public ResponseEntity<?> addDiaryEntry(@Valid @RequestBody DiaryEntryRequest entry) {
		return ResponseEntity.ok(diaryService.addEntry(entry));
	}

	/**
	 * Replace an existing entry document to the diary entries collection
	 * Only identical subclasses of a DiaryEntry can be replaced
	 * ie a user cannot update a NutritionEntry with a SleepEntry in its' place
	 * @param entry the DiaryEntryRequest object that the incoming request
	 *              is mapped to
	 *
	 * @return the persisted DiaryEntry
	 */

	@PutMapping("/diary/entries/{id}")
	public ResponseEntity<?> editDiaryEntry(@PathVariable String id, @Valid @RequestBody DiaryEntryRequest entry) {
		return ResponseEntity.ok(diaryService.editEntry(id, entry));
	}

	/**
	 * Delete an entry with a specific id
	 * @param id the id of the entry to be deleted
	 * @return a 204 No Content empty response message regardless
	 * 			if a Nutrition document was found and deleted or not
	 */

	@DeleteMapping("/diary/entries/{id}")
	public ResponseEntity<?> deleteDiaryEntry(@PathVariable String id) {
		diaryService.deleteEntryById(id);
		return ResponseEntity.noContent().build();
	}



}
