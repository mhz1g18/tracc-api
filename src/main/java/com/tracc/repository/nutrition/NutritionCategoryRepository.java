package com.tracc.repository.nutrition;

import com.tracc.models.nutrition.NutritionCategory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

@Qualifier("MongoRepo")
public interface NutritionCategoryRepository extends MongoRepository<NutritionCategory, String> {

    Optional<NutritionCategory> findByName(String name);

    @Query(" { 'name' : { $in: ?0 } } ")
    List<NutritionCategory> findByNames(List<String> names);


}
