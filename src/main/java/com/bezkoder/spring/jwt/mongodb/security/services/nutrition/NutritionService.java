package com.bezkoder.spring.jwt.mongodb.security.services.nutrition;

import com.bezkoder.spring.jwt.mongodb.exceptions.nutrition.NutritionNotFoundException;
import com.bezkoder.spring.jwt.mongodb.exceptions.userprofile.ProfileNotFoundException;
import com.bezkoder.spring.jwt.mongodb.models.nutrition.Nutrition;
import com.bezkoder.spring.jwt.mongodb.models.nutrition.NutritionCategory;
import com.bezkoder.spring.jwt.mongodb.models.user.UserProfile;
import com.bezkoder.spring.jwt.mongodb.repository.nutrition.NutritionCategoryRepository;
import com.bezkoder.spring.jwt.mongodb.repository.nutrition.NutritionRepository;
import com.bezkoder.spring.jwt.mongodb.repository.userprofile.UserProfileRepository;
import com.bezkoder.spring.jwt.mongodb.security.jwt.UserProfileUtils;
import com.bezkoder.spring.jwt.mongodb.security.services.user.UserDetailsImpl;
import com.mongodb.Mongo;
import com.mongodb.client.model.Updates;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NutritionService {

    private final NutritionRepository nutritionRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserProfileUtils userProfileUtils;
    private final NutritionCategoryRepository nutritionCategoryRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public NutritionService(NutritionRepository nutritionRepository,
                            UserProfileRepository userProfileRepository,
                            UserProfileUtils userProfileUtils,
                            NutritionCategoryRepository nutritionCategoryRepository,
                            MongoTemplate mongoTemplate) {
        this.nutritionRepository = nutritionRepository;
        this.userProfileRepository = userProfileRepository;
        this.userProfileUtils = userProfileUtils;
        this.nutritionCategoryRepository = nutritionCategoryRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<Nutrition> findAllNutrition() {
        String userId = userProfileUtils.getUserDetails().getId();
        return nutritionRepository.getAllNutrition(userId);
    }

    public Nutrition findNutritionById(String id) {
        String userId = userProfileUtils.getUserDetails().getId();
        return nutritionRepository.getNutrityionById(id, userId)
                                  .orElseThrow(() -> new NutritionNotFoundException(id));
    }

    public Nutrition addNutrition(Nutrition nutrition) {

        Collection<? extends GrantedAuthority>  authorities = userProfileUtils.getUserAuthorities();

        if(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            nutrition.setCreatedBy("ADMIN");
        } else {
            UserDetailsImpl userDetails = userProfileUtils.getUserDetails();
            nutrition.setCreatedBy(userDetails.getId());
        }

        Nutrition created =  nutritionRepository.save(nutrition);

        Query updateCategoriesQuery = new Query(Criteria.where("name").in(created.getCategories()));
        Update updateCategoriesNutrition = new Update().push("nutritionList", created);
        mongoTemplate.updateMulti(updateCategoriesQuery, updateCategoriesNutrition, NutritionCategory.class);

        return created;

    }


    public void deleteNutritionById(String id) {
        String userId = userProfileUtils.getUserDetails().getId();

        if(userProfileUtils.getUserAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
            userId = "ADMIN";

        Optional<Nutrition> nutrition = nutritionRepository.getNutrityionById(id, userId);

        nutritionRepository.deleteNutrityionById(id, userId);

        if(nutrition.isPresent()) {
            List<String> categories = nutrition.get().getCategories();
            Query popQuery = new Query(Criteria.where("name").in(categories));

            Query updateQuery = Query.query(Criteria.where("$id").is(new ObjectId(id)));
            Update updateCategories = new Update().pull("nutritionList", updateQuery);

            mongoTemplate.updateMulti(popQuery, updateCategories, NutritionCategory.class);
        }
    }

    public Nutrition editNutritionById(String id, Nutrition newNutrition) {
        String userId = userProfileUtils.getUserDetails().getId();
        Nutrition existing = nutritionRepository.getNutrityionById(id, userId)
                                                          .orElseThrow(() -> new NutritionNotFoundException(id));
        List<String> existingCategories = existing.getCategories();
        List<String> newCategories = newNutrition.getCategories();

        newNutrition.setId(id);
        newNutrition.setCreatedBy(existing.getCreatedBy());

        // Categories that are in existingCategories but not in newCategories
        // need to have a pull operation applied to their nutritionList array property
        //
        // Categories that are in newCategories but not in existingCategories
        // need to have a push operation applied to their nutritionList property

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

        return nutritionRepository.save(newNutrition);

    }



    public List<Nutrition> findByType(String type) {
        return nutritionRepository.findNutritionByType(type);
    }

    public List<Nutrition> findByName(String name) {
        return nutritionRepository.findByName(".*" + name + ".*");
    }

    public List<Nutrition> findByCategory(List<String> categoryNames) {
        List<NutritionCategory> categories =  nutritionCategoryRepository.findByNames(categoryNames);

        Set<Nutrition> results = new HashSet<>();

        categories.forEach(category -> {results.addAll(category.getNutritionList());});

        return new ArrayList<>(results);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public NutritionCategory addCategory(NutritionCategory category) {
        return nutritionCategoryRepository.save(category);
    }
}
