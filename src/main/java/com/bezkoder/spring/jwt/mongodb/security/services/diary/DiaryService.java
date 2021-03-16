package com.bezkoder.spring.jwt.mongodb.security.services.diary;

import com.bezkoder.spring.jwt.mongodb.exceptions.diary.DiaryEntryNotFoundException;
import com.bezkoder.spring.jwt.mongodb.exceptions.diary.DiaryNotFoundException;
import com.bezkoder.spring.jwt.mongodb.exceptions.nutrition.NutritionNotFoundException;
import com.bezkoder.spring.jwt.mongodb.models.diary.*;
import com.bezkoder.spring.jwt.mongodb.models.nutrition.EUnit;
import com.bezkoder.spring.jwt.mongodb.models.nutrition.Food;
import com.bezkoder.spring.jwt.mongodb.models.nutrition.Nutrition;
import com.bezkoder.spring.jwt.mongodb.models.nutrition.Supplement;
import com.bezkoder.spring.jwt.mongodb.payload.request.diary.DiaryEntryRequest;
import com.bezkoder.spring.jwt.mongodb.payload.request.diary.NutritionDiaryEntryRequest;
import com.bezkoder.spring.jwt.mongodb.payload.request.diary.SleepDiaryEntryRequest;
import com.bezkoder.spring.jwt.mongodb.repository.diary.DiaryEntryRepository;
import com.bezkoder.spring.jwt.mongodb.repository.diary.DiaryRepository;
import com.bezkoder.spring.jwt.mongodb.security.jwt.UserProfileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

/**
 * DiaryService
 *
 * Handles business logic related to
 * reading and writing documents in the database
 * in response to various HTTP requests queries
 * handed in by DiaryController
 */

@Service
public class DiaryService {

    /**
     * Dependency that have their instances injected by Spring's DI
     * @DiaryRepository used for persistence do the diaries collection
     * @DiaryEntryRepository used for persistence to the diary entries collection
     * @MongoTemplate used for additional query operations
     * @UserProfileUtils used for information regarding the user
     *                   extracted from Spring Security's context
     */

    private final DiaryRepository diaryRepository;
    private final DiaryEntryRepository diaryEntryRepository;
    private final UserProfileUtils userProfileUtils;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public DiaryService(@Qualifier("MongoRepo") DiaryRepository diaryRepository,
                        @Qualifier("MongoRepo") DiaryEntryRepository diaryEntryRepository,
                        UserProfileUtils userProfileUtils,
                        MongoTemplate mongoTemplate) {
        this.diaryRepository = diaryRepository;
        this.diaryEntryRepository = diaryEntryRepository;
        this.userProfileUtils = userProfileUtils;
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Retrieve a Diary by its' id
     * @param id the id used to query for the diary
     * @return the fetched Diary document from the db
     * @throws DiaryNotFoundException
     */

    public Diary findById(String id) {
        return diaryRepository.findById(id)
                .orElseThrow(() -> new DiaryNotFoundException(id));
    }

    /**
     * Retrieve a Diary by its' user_id field
     * @return the fetched Diary document from the db
     * @throws DiaryNotFoundException
     */

    public Diary findByUserId() {

        String userId = userProfileUtils.getUserDetails().getId();

        return diaryRepository.findByUserId(userId)
                .orElseThrow(() -> new DiaryNotFoundException( " (user id ) " + userId));
    }

    /**
     * Retrieve a Diary Entry by its' id
     * A user may only fetch entries created by them
     * @param id the id used in the query
     * @return the fetched Diary Entry document from the db
     * @throws DiaryEntryNotFoundException
     */

    public DiaryEntry getDiaryEntryById(String id) {
        String userId = userProfileUtils.getUserDetails().getId();
        return diaryEntryRepository.getDiaryEntryById(id, userId)
                .orElseThrow(() -> new DiaryEntryNotFoundException(id));
    }

    /**
     * Retrieve diary entries for a specific date
     * @param date the date to query by
     * @return DiaryEntryList containing entries for that date
     *         if no entries for that date exist return an empty DiaryEntryList
     */

    public DiaryEntryList getDiaryEntriesByDate(LocalDate date) {

        String userId = userProfileUtils.getUserDetails().getId();

        Query query = new Query();
        query.addCriteria(Criteria.where("entries."+date).exists(true).and("userId").is(userId));
        query.fields().include("entries."+date);

        Diary d =  mongoTemplate.findOne(query, Diary.class);

        if(d != null) {
            return d.getEntries().get(date);
        }

        return new DiaryEntryList();
    }

    /**
     * Retrieve diary entries for a specific date
     * @param date the date to query by
     * @return DiaryEntryList containing entries for that date
     *         if no entries for that date exist return an empty DiaryEntryList
     */

    public List<DiaryEntry> getEntriesByType(String type) {

        String userId = userProfileUtils.getUserDetails().getId();

        Query query = new Query();
        query.addCriteria(Criteria.where("type").is(type).and("createdBy").is(userId));

        List<DiaryEntry> d =  mongoTemplate.find(query, DiaryEntry.class);

        return d;

    }


    /**
     * Persist a diary entry to the diary_entries collection
     * and add a reference to it in the user's diary entries list
     *
     * @DiaryEntryRequest is an abstract class, classes such as SleepDiaryEntryRequest,
     *                    and NutritionDiaryEntryRequest inherit from it.
     *                    Therefore, depending on the type of request object, different types
     *                    of entry objects are constructed, ie. SleepEntry, NutritionEntry
     *
     * @param entry the object holding the data needed for a DiaryEntry
     *              object to be created and persisted
     * @return persisted DiaryEntry
     * @throws DiaryEntryNotFoundException
     */

    public DiaryEntry addEntry(DiaryEntryRequest entry) {
        LocalDate date = java.time.LocalDate.now(); /*.toString();*/
        Diary userDiary = this.findByUserId();

        DiaryEntry diaryEntry = parseDiaryEntryRequest(entry);

        diaryEntry.setCreatedBy(userProfileUtils.getUserDetails().getId());
        DiaryEntry saved = diaryEntryRepository.save(diaryEntry);
        Map<LocalDate, DiaryEntryList> diaryEntries = userDiary.getEntries();

        if(!diaryEntries.containsKey(date)) {
            diaryEntries.put(date, new DiaryEntryList());
        }

        diaryEntries.get(date).diaryEntries.add(saved);


        userDiary.setEntries(diaryEntries);
        diaryRepository.save(userDiary);

        return saved;
    }

    /**
     * Edit a diary entry document by its id
     * A user may only edit personally created documents
     *
     * @param id of the document to edit
     * @param newDiaryEntry modified version of the document
     * @return the persisted object
     */

    public DiaryEntry editEntry(String id, DiaryEntryRequest newDiaryEntry) {

        String userId = userProfileUtils.getUserDetails().getId();
        DiaryEntry existing =  diaryEntryRepository.getDiaryEntryById(id, userId)
                .orElseThrow(() -> new DiaryEntryNotFoundException(id));

        DiaryEntry diaryEntry = parseDiaryEntryRequest(newDiaryEntry);

        diaryEntry.setCreatedBy(existing.getCreatedBy());
        diaryEntry.setId(existing.getId());

        return diaryEntryRepository.save(diaryEntry);

    }

    /**
     * Delete a diary entry document by its id
     * A user may only delete personally created documents
     *
     * @param id of the document to delete
     */

    public void deleteEntryById(String id) {
        String userId = userProfileUtils.getUserDetails().getId();
        diaryEntryRepository.deleteDiaryEntryById(id, userId);
    }


    /**
     * Parse a DiaryEntryRequest in order to construct a DiaryEntry object
     * @param diaryEntryRequest DiaryEntryRequest to be parsed
     * @return the constructed DiaryEntry object
     */

    private DiaryEntry parseDiaryEntryRequest(DiaryEntryRequest diaryEntryRequest) {
        DiaryEntry diaryEntry;

        if(diaryEntryRequest instanceof SleepDiaryEntryRequest) {
            diaryEntry = new SleepDiaryEntry();
            ((SleepDiaryEntry) diaryEntry).setDuration(((SleepDiaryEntryRequest) diaryEntryRequest).getDuration());
            ((SleepDiaryEntry) diaryEntry).setNotes(((SleepDiaryEntryRequest) diaryEntryRequest).getNotes());
        }
        else if(diaryEntryRequest instanceof NutritionDiaryEntryRequest) {
            diaryEntry = new NutritionDiaryEntry();


            // list containing all foods from the incoming request
            List<Food> foods = ((NutritionDiaryEntryRequest) diaryEntryRequest).getFoodList();
            // list containing foods with macro and micro values calculated
            List<Food> normalizedFoods = new ArrayList<>();

            Map<String, Integer> microNutrients = new HashMap<>();
            List<Supplement> allMicronutrients = new ArrayList<>();

            if(!((NutritionDiaryEntryRequest) diaryEntryRequest).isCalculatedValues()) {
                System.out.println("calculating values");
                foods.forEach(food -> {
                    EUnit unit = food.getUnit();
                    int quantity = food.getQuantity();

                    if(unit == EUnit.UNIT_OZ) {
                        float temp = quantity / 0.035274F; // OZ to gram formula
                        quantity = Math.round(temp);
                    }

                    food.setCalories(food.getCalories() * quantity / 100);
                    food.setProtein(food.getProtein() * quantity / 100);
                    food.setCarbs(food.getCarbs() * quantity / 100);
                    food.setFiber(food.getFiber() * quantity / 100);
                    food.setFats(food.getFats() * quantity / 100);
                    food.setSugars(food.getSugars() * quantity / 100);
                    food.setTransfats(food.getTransfats() * quantity / 100);

                    for (Supplement micro : food.getMicronutrients()) {
                        micro.setQuantity(micro.getQuantity() * quantity / 100);
                    }

                    normalizedFoods.add(food);

                });
            } else {
                normalizedFoods.addAll(foods);
            }

            ((NutritionDiaryEntry) diaryEntry).setFoodList(normalizedFoods);
            ((NutritionDiaryEntry) diaryEntry).setSupplementList(((NutritionDiaryEntryRequest) diaryEntryRequest).getSupplementList());

            // Calculate values
            // cals, prot, carbs, fats, trans, sugars, fiber
            float[] macroNutrients = { 0, 0, 0, 0, 0, 0, 0 };

            normalizedFoods.forEach(food -> {
                macroNutrients[0] = macroNutrients[0] + food.getCalories();
                macroNutrients[1] = macroNutrients[1] + food.getProtein();
                macroNutrients[2] = macroNutrients[2] + food.getCarbs();
                macroNutrients[3] = macroNutrients[3] + food.getFats();
                macroNutrients[4] = macroNutrients[4] + food.getTransfats();
                macroNutrients[5] = macroNutrients[5] + food.getSugars();
                macroNutrients[6] = macroNutrients[6] + food.getFiber();

                food.getMicronutrients().forEach(micro -> {

                    Supplement copyMicro = new Supplement();
                    String microName = micro.getName();
                    String microId = micro.getId();
                    EUnit microUnit = micro.getUnit();
                    int microQuantity = micro.getQuantity();

                    copyMicro.setName(microName);
                    copyMicro.setId(microId);
                    copyMicro.setUnit(microUnit);
                    copyMicro.setQuantity(microQuantity);

                    allMicronutrients.add(copyMicro);
                    if(microNutrients.containsKey(copyMicro.getId())) {
                        int microQ = microNutrients.get(copyMicro.getId());
                        microNutrients.put(micro.getId(), Math.round(microQ + copyMicro.getQuantity()));
                    } else {
                        microNutrients.put(copyMicro.getId(), copyMicro.getQuantity());
                    }
                });


            });

            ((NutritionDiaryEntry) diaryEntry).setCalories(macroNutrients[0]);
            ((NutritionDiaryEntry) diaryEntry).setProtein(macroNutrients[1]);
            ((NutritionDiaryEntry) diaryEntry).setCarbs(macroNutrients[2]);
            ((NutritionDiaryEntry) diaryEntry).setFats(macroNutrients[3]);
            ((NutritionDiaryEntry) diaryEntry).setTransfats(macroNutrients[4]);
            ((NutritionDiaryEntry) diaryEntry).setSugars(macroNutrients[5]);
            ((NutritionDiaryEntry) diaryEntry).setFiber(macroNutrients[6]);

            Set<Supplement> normalizedMicros = new HashSet<>();

            for(String suppId : microNutrients.keySet()) {
                Supplement supp = allMicronutrients.stream().filter(supplement -> supplement.getId() == suppId).findFirst()
                        .orElse(null);

                if(supp == null)
                    continue;

                supp.setQuantity(microNutrients.get(supp.getId()));
                normalizedMicros.add(supp);
            }

            ((NutritionDiaryEntry) diaryEntry).setMicronutrients(normalizedMicros);

        }
        else {
            return null;
        }

        return diaryEntry;
    }
}
