package com.bezkoder.spring.jwt.mongodb.repository.nutrition;

import com.bezkoder.spring.jwt.mongodb.models.nutrition.NutritionCategory;
import com.bezkoder.spring.jwt.mongodb.models.user.ERole;
import com.bezkoder.spring.jwt.mongodb.models.user.Role;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Qualifier("MongoRepo")
public interface NutritionCategoryRepository extends MongoRepository<NutritionCategory, String> {

    Optional<NutritionCategory> findByName(String name);

    @Query(" { 'name' : { $in: ?0 } } ")
    List<NutritionCategory> findByNames(List<String> names);


}
