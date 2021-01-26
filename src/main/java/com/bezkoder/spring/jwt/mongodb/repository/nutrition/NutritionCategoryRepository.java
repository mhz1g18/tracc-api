package com.bezkoder.spring.jwt.mongodb.repository.nutrition;

import com.bezkoder.spring.jwt.mongodb.models.nutrition.NutritionCategory;
import com.bezkoder.spring.jwt.mongodb.models.user.ERole;
import com.bezkoder.spring.jwt.mongodb.models.user.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NutritionCategoryRepository extends MongoRepository<NutritionCategory, String> {

    Optional<NutritionCategory> findByName(String name);

    @Query(" { 'name' : { $in: ?0 } } ")
    List<NutritionCategory> findByNames(List<String> names);


}
