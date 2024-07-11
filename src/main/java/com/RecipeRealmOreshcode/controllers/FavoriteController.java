package com.RecipeRealmOreshcode.controllers;

import com.RecipeRealmOreshcode.entities.Recipe;
import com.RecipeRealmOreshcode.services.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping("/add/{recipeId}")
    @Operation(summary = "Adds a Recipe to Favorites List", description = "Adds a Recipe to an User's Favorite List.")
    public ResponseEntity<Void> addRecipeToFavorites(@PathVariable Long recipeId, Principal principal) {
        favoriteService.addRecipeToFavorites(recipeId, principal.getName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{recipeId}")
    @Operation(summary = "Remove a Recipe from Favorites", description = "Removes a Recipe from User's Favorite List using the RecipeId")
    public ResponseEntity<Void> removeRecipeFromFavorites(@PathVariable Long recipeId, Principal principal) {
        favoriteService.removeRecipeFromFavorites(recipeId, principal.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recipes")
    @Operation(summary = "Retrieve all Favorited Recipes by User", description = "Retrieves a List of Recipes added to Favorite, using User's username.")
    public ResponseEntity<List<Recipe>> getFavoriteRecipes(Principal principal) {
        List<Recipe> favoriteRecipes = favoriteService.getFavoriteRecipesByUsername(principal.getName());
        return ResponseEntity.ok(favoriteRecipes);
    }
}
