package com.bezkoder.spring.jwt.mongodb.security.services.nutrition;

import com.bezkoder.spring.jwt.mongodb.exceptions.nutrition.NutritionNotFoundException;
import com.bezkoder.spring.jwt.mongodb.models.nutrition.*;
import com.bezkoder.spring.jwt.mongodb.payload.request.nutrition.FoodRequest;
import com.bezkoder.spring.jwt.mongodb.payload.request.nutrition.NutritionRequest;
import com.bezkoder.spring.jwt.mongodb.payload.request.nutrition.SupplementRequest;
import com.bezkoder.spring.jwt.mongodb.repository.nutrition.NutritionCategoryRepository;
import com.bezkoder.spring.jwt.mongodb.repository.nutrition.NutritionRepository;
import com.bezkoder.spring.jwt.mongodb.security.jwt.UserProfileUtils;
import com.bezkoder.spring.jwt.mongodb.security.services.user.UserDetailsImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * NutritionService
 *
 * Handles business logic related to
 * reading and writing documents in the database
 * in response to various HTTP requests queries
 * handed in by NutritionController
 */

@Service
public class NutritionService {

    /**
     * Dependency that have their instances injected by Spring's DI
     * @NutritionRepository used for persistence do the nutrition collection
     * @NutritionCategoryRepository used for persistence to the nutrition_categories collection
     * @MongoTemplate used for some additional queries to the database
     * @UserProfileUtils used for information regarding the user
     *                   extracted from Spring Security's context
     */

    private final NutritionRepository nutritionRepository;
    private final UserProfileUtils userProfileUtils;
    private final NutritionCategoryRepository nutritionCategoryRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public NutritionService(NutritionRepository nutritionRepository,
                            UserProfileUtils userProfileUtils,
                            NutritionCategoryRepository nutritionCategoryRepository,
                            MongoTemplate mongoTemplate) {
        this.nutritionRepository = nutritionRepository;
        this.userProfileUtils = userProfileUtils;
        this.nutritionCategoryRepository = nutritionCategoryRepository;
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Fetch all nutrition documents created by admin or the user
     * Note: does not include Nutrition documents not created by
     *       the user issuing the request or an admin
     *
     * @return list of Nutrition documents created by admin or the user
     */

    public List<Nutrition> findAllNutrition() {
        String userId = userProfileUtils.getUserDetails().getId();
        return nutritionRepository.getAllNutrition(userId);
    }

    /**
     * Fetch Nutrition document by the _id field
     * Results are not limited the documents created by the user or admin
     * Note: Users must be able to fetch individual documents after a search
     *
     * @param id the document's _id field
     * @return the Nutrition document with the specified id
     * @throws NutritionNotFoundException
     */

    public Nutrition findNutritionById(String id) {
        return nutritionRepository.findById(id)
                                  .orElseThrow(() -> new NutritionNotFoundException(id));
    }

    /**
     * Handles a NutritionRequest to construct a Nutrition object
     * and persist it to tha Nutrition collection in the database.
     * A NutritionRequest may be a FoodRequest or a SupplementRequest
     *
     * @param nutritionRequest the incoming request
     * @return the persisted Nutrition object
     * @see NutritionRequest
     * @see this.parseNutritionRequest()
     */

    public Nutrition addNutrition(NutritionRequest nutritionRequest) {

        /**
         * Account's with authority role of an admin have
         * the purpose of only populating the datbase with "default" data
         *
         * Nutrition documents tagged as cready_by admin serve
         * as the default nutrition list of an user
         */

        Collection<? extends GrantedAuthority>  authorities = userProfileUtils.getUserAuthorities();

        Nutrition nutrition = parseNutritionRequest(nutritionRequest);

        if(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            nutrition.setCreatedBy("ADMIN");
        } else {
            UserDetailsImpl userDetails = userProfileUtils.getUserDetails();
            nutrition.setCreatedBy(userDetails.getId());
        }

        Nutrition created =  nutritionRepository.save(nutrition);

        // Update the relevant NutritionCategory documents

        Query updateCategoriesQuery = new Query(Criteria.where("name").in(created.getCategories()));
        Update updateCategoriesNutrition = new Update().push("nutritionList", created);
        mongoTemplate.updateMulti(updateCategoriesQuery, updateCategoriesNutrition, NutritionCategory.class);

        return nutrition;

    }

    /**
     * Delete a nutrition document by its id
     * A user may only delete personally created documents
     *
     * @param id of the document to delete
     */

    public void deleteNutritionById(String id) {
        String userId = userProfileUtils.getUserDetails().getId();

        if(userProfileUtils.getUserAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
            userId = "ADMIN";

        Optional<Nutrition> nutrition = nutritionRepository.getNutritionById(id, userId);

        nutritionRepository.deleteNutritionById(id, userId);

        // Remove the nutrition from Lists in the categories

        if(nutrition.isPresent()) {
            List<String> categories = nutrition.get().getCategories();
            Query popQuery = new Query(Criteria.where("name").in(categories));

            Query updateQuery = Query.query(Criteria.where("$id").is(new ObjectId(id)));
            Update updateCategories = new Update().pull("nutritionList", updateQuery);

            mongoTemplate.updateMulti(popQuery, updateCategories, NutritionCategory.class);
        }
    }

    /**
     * Edit a nutrition document by its id
     * A user may only edit personally created documents
     *
     * @param id of the document to edit
     * @param newNutritionRequest modified version of the document
     * @return the persisted object
     */

    public Nutrition editNutritionById(String id, NutritionRequest newNutritionRequest) {

        String userId = userProfileUtils.getUserDetails().getId();
        Nutrition existing = nutritionRepository.getNutritionById(id, userId)
                                                          .orElseThrow(() -> new NutritionNotFoundException(id));

        Nutrition newNutrition = parseNutritionRequest(newNutritionRequest);

        newNutrition.setId(id);
        newNutrition.setCreatedBy(existing.getCreatedBy());

        List<String> existingCategories = existing.getCategories();
        List<String> newCategories = newNutrition.getCategories();

        // Categories that are in existingCategories but not in newCategories
        // need to have a pull operation applied to their nutritionList array property
        //
        // Categories that are in newCategories but not in existingCategories
        // need to have a push operation applied to their nutritionList property

        if(!(new HashSet<>(existingCategories).equals(new HashSet<>(newCategories)))) {

            System.out.println("Updating categories");
            List<String> pullOperations = new ArrayList<>(existingCategories);
            pullOperations.removeAll(newCategories);

            List<String> pushOperations = new ArrayList<>(newCategories);
            pushOperations.removeAll(existingCategories);

            Query popQuery = new Query(Criteria.where("name").in(pullOperations));
            Query updateQuery = Query.query(Criteria.where("$id").is(new ObjectId(id)));
            Update updateCategories = new Update().pull("nutritionList", updateQuery);
            mongoTemplate.updateMulti(popQuery, updateCategories, NutritionCategory.class);

            Query pushQuery = new Query(Criteria.where("name").in(pushOperations));
            Update updateNewCategories = new Update().push( "nutritionList", newNutrition);
            mongoTemplate.updateMulti(pushQuery, updateNewCategories, NutritionCategory.class);
        } else {
            System.out.println("Updating categories not needed!");
        }

        return nutritionRepository.save(newNutrition);

    }

    /**
     * Return all documents with the specified type
     *
     * @param type  the type to filter by
     * @return List of all results
     */

    public List<Nutrition> findByType(String type) {
        return nutritionRepository.findNutritionByType(type);
    }

    /**
     * Search for nutrition documents by name
     *
     * @param name the name to search by
     * @return List of all results
     */

    public List<Nutrition> findByName(String name) {
        return nutritionRepository.findByName(".*" + name + ".*");
    }

    /**
     * Fetch all nutrition documents in a list of categories
     *
     * @param categoryNames the categories
     * @return List of the results
     */

    public List<Nutrition> findByCategory(List<String> categoryNames)   {
        List<NutritionCategory> categories =  nutritionCategoryRepository.findByNames(categoryNames);

        Set<Nutrition> results = new HashSet<>();

        categories.forEach(category -> {results.addAll(category.getNutritionList());});

        return new ArrayList<>(results);

    }

    /**
     * Add a category document
     *
     * @param category object to be persisted
     * @return the persisted object
     */

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public NutritionCategory addCategory(NutritionCategory category) {
        return nutritionCategoryRepository.save(category);
    }

    /**
     * Parse a NutritionRequest in order to construct a Nutrition object
     * @param nutritionRequest NutritionRequest to be parsed
     * @return the constructed Nutrition object
     */

    private Nutrition parseNutritionRequest(NutritionRequest nutritionRequest) {
        Nutrition nutrition;

        if(nutritionRequest instanceof FoodRequest) {
            nutrition = new Food();

            FoodRequest castRequest = ((FoodRequest) nutritionRequest);

            // Get the food's quantity and measurement unit in order to
            // calculate the nutritional values per 100 grams
            // e.g. 100 cals / 5 OZ = x cals / ( 5 OZ) to grams
            //      solve for x

            int quantity = castRequest.getQuantity();
            EUnit unit = castRequest.getUnit();

            int coef = 100; // since we are calculating values per 100 grams

            if(unit == EUnit.UNIT_OZ) {
                float temp = quantity / 0.035274F; // OZ to gram formula
                quantity = Math.round(temp);
            }

            // x/100grams = foodRequest.getCalories()/quantityGrams
            // x = foodRequest.getCalories() * 100 / quanityGrams
            ((Food) nutrition).setCalories(coef * castRequest.getCalories() / quantity);
            ((Food) nutrition).setProtein(coef * castRequest.getProtein() / quantity);
            ((Food) nutrition).setCarbs(coef * castRequest.getCarbs() / quantity);
            ((Food) nutrition).setFats(coef * castRequest.getFats() / quantity);
            ((Food) nutrition).setTransfats(coef * castRequest.getTransFats() / quantity);
            ((Food) nutrition).setFiber(coef * castRequest.getFiber() / quantity);
            ((Food) nutrition).setSugars(coef * castRequest.getSugars() / quantity);

            // Add micros to food
            Map<String, Integer> micros = castRequest.getMicronutrientIds();

            Query findSupplements = new Query(Criteria.where("id").in(micros.keySet()));
            List<Supplement> supplements = mongoTemplate.find(findSupplements, Supplement.class);

            System.out.println("Ids in request:");
            for(String key : micros.keySet()) {
                System.out.println(key);
            }

            System.out.println("found supps");
            for(Supplement supp : supplements) {
                System.out.println(supp.getName());
            }

            supplements.forEach(supplement -> supplement.setQuantity(coef * micros.get(supplement.getId()) / castRequest.getQuantity() ));

            ((Food) nutrition).setMicronutrients((ArrayList<Supplement>)supplements);


        } else if(nutritionRequest instanceof SupplementRequest) {
            nutrition = new Supplement();
            ((Supplement) nutrition).setUnit(nutritionRequest.getUnit());
        } else {
            throw new IllegalArgumentException("Invalid type " + nutritionRequest.getType());
        }

        nutrition.setName(nutritionRequest.getName());
        nutrition.setDescription(nutritionRequest.getDescription());
        nutrition.setCategories(nutritionRequest.getCategories());

        return  nutrition;
    }
}
