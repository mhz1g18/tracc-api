package com.tracc.controllers.nutrition;

import com.tracc.exceptions.nutrition.NutritionNotFoundException;
import com.tracc.models.nutrition.Nutrition;
import com.tracc.models.nutrition.NutritionCategory;
import com.tracc.payload.request.nutrition.NutritionRequest;
import com.tracc.security.services.nutrition.NutritionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * NutritionController
 *
 * Handles URI mappings and building appropriate responses
 * for CRUD operations and queries
 * related to documents in the Nutrition collection
 *
 * The actual logic of those operations
 * is performed in the NutritionService
 * @see NutritionService
 *
 * Annotated with @PreAuthorize("isAuthenticated()")
 * to ensure only authenticated users may access the resources
 */

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

    /**
     * Get a list of all available nutrition
     * to the user requesting it
     *
     * @return list of Nutrition results
     */

    @GetMapping("/")
    public ResponseEntity<?> getAvailableNutrition() {
        List<Nutrition> nutrition = nutritionService.findAllNutrition();
        return ResponseEntity.ok(nutrition);
    }

    /**
     * Return a Nutrition document with a specific id
     * @param id the id of the looked-up document
     * @return the matching Nutrition object
     * @throws NutritionNotFoundException
     */

    @GetMapping("/{id}")
    public ResponseEntity<?> getNutritionById(@PathVariable String id) {
        Nutrition nutrition = nutritionService.findNutritionById(id);
        return ResponseEntity.ok(nutrition);
    }

    /**
     * Add a new Nutrition document to the database
     * @param nutritionRequest a validated NutritionRequest containing all needed fields
     * @return the persisted Nutrition object
     */

    @PostMapping("/")
    public ResponseEntity<?> addNutrition(@Valid @RequestBody NutritionRequest nutritionRequest) {
        return ResponseEntity.ok(nutritionService.addNutrition(nutritionRequest));
    }

    /**
     * Delete a Nutrition document by finding them by their id
     * @param id the id of the Nutrition document to delete
     * @return a 204 No Content empty response message regardless
     *          if a Nutrition document was found and deleted or not
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNutrition(@PathVariable String id) {
        nutritionService.deleteNutritionById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Modify an existing nutrition document
     * @param id
     * @param newNutrition
     * @return
     */

    @PutMapping("/{id}")
    public ResponseEntity<?> editNutrition(@PathVariable String id, @Valid @RequestBody NutritionRequest newNutrition) {
        Nutrition nutrition = nutritionService.editNutritionById(id, newNutrition);
        return ResponseEntity.ok(nutrition);
    }

    /**
     * Handles queries searching for Nutrition by type
     * @param type the specified type
     * @return list of results
     */

    @GetMapping(value = "/search", params = "type")
    public ResponseEntity<?> findByType(@RequestParam String type) {
        return ResponseEntity.ok(nutritionService.findByType(type.toUpperCase()));
    }

    /**
     * Handles queries searching for Nutrition by name
     * @param name string to use a regex match on
     * @return list of results
     */

    @GetMapping(value = "/search", params = "name")
    public ResponseEntity<?> findByName(@RequestParam String name) {
        return ResponseEntity.ok(nutritionService.findByName(name));
    }

    /**
     * Handles queries searching for Nutrition by category
     * @param categories the specified categories
     * @return list of results
     */

    @GetMapping(value = "/search", params = "categories")
    public ResponseEntity<?> findByCategory(@RequestParam List<String> categories) {
        return ResponseEntity.ok(nutritionService.findByCategory(categories));
    }

    /**
     * Handles creating a new NutritionCategory document
     * @param category valid NutritionCategory object
     * @return the persisted object
     */

    @PostMapping("/category/")
    public ResponseEntity<?> addCategory(@Valid @RequestBody NutritionCategory category) {
        return ResponseEntity.ok(nutritionService.addCategory(category));
    }
}
