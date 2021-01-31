package com.bezkoder.spring.jwt.mongodb;

import com.bezkoder.spring.jwt.mongodb.controllers.nutrition.NutritionController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringBootSecurityJwtMongodbApplicationTests {

	@Autowired
	NutritionController nutritionController;

	@Test
	void contextLoads() {
		assertThat(nutritionController).isNot(null);
	}

}
