package com.bezkoder.spring.jwt.mongodb.controllers.nutrition;

import com.bezkoder.spring.jwt.mongodb.models.nutrition.Nutrition;
import com.bezkoder.spring.jwt.mongodb.models.nutrition.NutritionCategory;
import com.bezkoder.spring.jwt.mongodb.security.services.nutrition.NutritionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/nutrition")
@PreAuthorize("isAuthenticated()")
public class NutritionController {

    private final NutritionService nutritionService;

    @Autowired
    public NutritionController(NutritionService nutritionService) {
        this.nutritionService = nutritionService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getNutrition() {
        List<Nutrition> nutrition = nutritionService.findAllNutrition();
        return ResponseEntity.ok(nutrition);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNutritionById(@PathVariable String id) {
        Nutrition nutrition = nutritionService.findNutritionById(id);
        return ResponseEntity.ok(nutrition);
    }

    @PostMapping("/")
    public ResponseEntity<?> addNutrition(@Valid @RequestBody Nutrition nutrition) {
        return ResponseEntity.ok(nutritionService.addNutrition(nutrition));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNutrition(@PathVariable String id) {
        nutritionService.deleteNutritionById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editNutrition(@PathVariable String id, @Valid @RequestBody Nutrition newNutrition) {
        Nutrition nutrition = nutritionService.editNutritionById(id, newNutrition);
        return ResponseEntity.ok(nutrition);
    }

    @GetMapping(value = "/search", params = "type")
    public ResponseEntity<?> findByType(@RequestParam String type) {
        return ResponseEntity.ok(nutritionService.findByType(type.toUpperCase()));
    }

    @GetMapping(value = "/search", params = "name")
    public ResponseEntity<?> findByName(@RequestParam String name) {
        return ResponseEntity.ok(nutritionService.findByName(name));
    }

    @GetMapping(value = "/search", params = "categories")
    public ResponseEntity<?> findByCategory(@RequestParam List<String> categories) {
        return ResponseEntity.ok(nutritionService.findByCategory(categories));
    }

    @PostMapping("/category/")
    public ResponseEntity<?> addCategory(@Valid @RequestBody NutritionCategory category) {
        return ResponseEntity.ok(nutritionService.addCategory(category));
    }
}
