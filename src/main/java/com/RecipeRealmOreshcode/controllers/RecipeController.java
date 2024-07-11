package com.RecipeRealmOreshcode.controllers;

import com.RecipeRealmOreshcode.dtos.RecipeDto;
import com.RecipeRealmOreshcode.entities.Recipe;
import com.RecipeRealmOreshcode.services.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping("/create")
    @Operation(summary = "Creates a new Recipe", description = "Creates a new recipe using RecipeDto and Username and then adds it to the system")
    public ResponseEntity<Recipe> createRecipe(@RequestBody @Valid RecipeDto recipeDto, Principal principal) {
        Recipe createdRecipe = recipeService.createRecipe(recipeDto, principal.getName());
        return ResponseEntity.created(URI.create("/api/recipes/" + createdRecipe.getId())).body(createdRecipe);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a recipe by its ID", description = "Retrieves an existing recipe by it's ID")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        Recipe recipe = recipeService.getRecipeById(id);
        return ResponseEntity.ok(recipe);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Recipe", description = "Updates a Recipe's contents. Found by ID.")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody @Valid RecipeDto recipeDto) {
        Recipe updatedRecipe = recipeService.updateRecipe(id, recipeDto);
        return ResponseEntity.ok(updatedRecipe);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a Recipe", description = "Deletes a recipe using by it's ID.")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recipes")
    @Operation(summary = "Search a recipe by keyword AND category", description = "Searches for recipes that match BOTH the keyword and the category")
    public ResponseEntity<List<Recipe>> searchRecipes(@RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false) String category) {
        List<Recipe> recipes = recipeService.searchRecipes(keyword, category);
        return ResponseEntity.ok(recipes);
    }

    @PostMapping("/{id}/like")
    @Operation(summary = "Leaves a Like on a Recipe", description = "Using the Recipe ID, adds 1 Like to the like counter")
    public ResponseEntity<Void> likeRecipe(@PathVariable Long id) {
        recipeService.likeRecipe(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/dislike")
    @Operation(summary = "Leaves a Dislike on a Recipe", description = "Using the Recipe ID, adds 1 Dislike to the dislike counter")
    public ResponseEntity<Void> dislikeRecipe(@PathVariable Long id) {
        recipeService.dislikeRecipe(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "Retrieves all Recipes created by an user", description = "Retrieves all recipes made by an User, using their User ID")
    public ResponseEntity<List<Recipe>> getRecipesByUser(@PathVariable Long id) {
        List<Recipe> recipes = recipeService.getRecipesByUser(id);
        return ResponseEntity.ok(recipes);
    }
}
