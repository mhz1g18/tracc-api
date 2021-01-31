package com.bezkoder.spring.jwt.mongodb;

import com.bezkoder.spring.jwt.mongodb.controllers.nutrition.NutritionController;
import com.bezkoder.spring.jwt.mongodb.models.diary.Diary;
import com.bezkoder.spring.jwt.mongodb.models.nutrition.EUnit;
import com.bezkoder.spring.jwt.mongodb.models.nutrition.Food;
import com.bezkoder.spring.jwt.mongodb.models.nutrition.Supplement;
import com.bezkoder.spring.jwt.mongodb.models.user.UserProfile;
import com.bezkoder.spring.jwt.mongodb.payload.request.nutrition.FoodRequest;
import com.bezkoder.spring.jwt.mongodb.payload.request.nutrition.NutritionRequest;
import com.bezkoder.spring.jwt.mongodb.payload.request.nutrition.SupplementRequest;
import com.bezkoder.spring.jwt.mongodb.security.jwt.UserProfileUtils;
import com.bezkoder.spring.jwt.mongodb.security.services.nutrition.NutritionService;
import com.bezkoder.spring.jwt.mongodb.security.services.user.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(NutritionController.class)
//@EnableMongoRepositories(basePackages = {"com.bezkoder.spring.jwt.mongodb.repository"})
public class NutritionControllerTest {

    @MockBean
    NutritionService nutritionService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_created_nutrition() throws Exception {
        SupplementRequest supplementRequest = new SupplementRequest();

        Supplement supp = new Supplement();
        supp.setName(supplementRequest.getName());

        when(nutritionService.addNutrition(any(NutritionRequest.class))).thenReturn(supp);

        mockMvc.perform(post("/api/nutrition")
                .content(mapper.writeValueAsString(supplementRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(supplementRequest.getName()));


    }

}
