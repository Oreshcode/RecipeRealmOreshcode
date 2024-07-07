package com.RecipeRealmOreshcode.controllers;

import com.RecipeRealmOreshcode.dtos.RecipeDto;
import com.RecipeRealmOreshcode.entities.Recipe;
import com.RecipeRealmOreshcode.services.RecipeService;
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
    public ResponseEntity<Recipe> createRecipe(@RequestBody @Valid RecipeDto recipeDto, Principal principal) {
        Recipe createdRecipe = recipeService.createRecipe(recipeDto, principal.getName());
        return ResponseEntity.created(URI.create("/api/recipes/" + createdRecipe.getId())).body(createdRecipe);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        Recipe recipe = recipeService.getRecipeById(id);
        return ResponseEntity.ok(recipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody @Valid RecipeDto recipeDto) {
        Recipe updatedRecipe = recipeService.updateRecipe(id, recipeDto);
        return ResponseEntity.ok(updatedRecipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recipes")
    public ResponseEntity<List<Recipe>> searchRecipes(@RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false) String category) {
        List<Recipe> recipes = recipeService.searchRecipes(keyword, category);
        return ResponseEntity.ok(recipes);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> likeRecipe(@PathVariable Long id) {
        recipeService.likeRecipe(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/dislike")
    public ResponseEntity<Void> dislikeRecipe(@PathVariable Long id) {
        recipeService.dislikeRecipe(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Recipe>> getRecipesByUser(@PathVariable Long id) {
        List<Recipe> recipes = recipeService.getRecipesByUser(id);
        return ResponseEntity.ok(recipes);
    }
}
