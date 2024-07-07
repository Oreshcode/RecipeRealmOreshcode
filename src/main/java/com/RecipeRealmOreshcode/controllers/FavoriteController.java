package com.RecipeRealmOreshcode.controllers;

import com.RecipeRealmOreshcode.entities.Recipe;
import com.RecipeRealmOreshcode.services.FavoriteService;
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
    public ResponseEntity<Void> addRecipeToFavorites(@PathVariable Long recipeId, Principal principal) {
        favoriteService.addRecipeToFavorites(recipeId, principal.getName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{recipeId}")
    public ResponseEntity<Void> removeRecipeFromFavorites(@PathVariable Long recipeId, Principal principal) {
        favoriteService.removeRecipeFromFavorites(recipeId, principal.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recipes")
    public ResponseEntity<List<Recipe>> getFavoriteRecipes(Principal principal) {
        List<Recipe> favoriteRecipes = favoriteService.getFavoriteRecipesByUsername(principal.getName());
        return ResponseEntity.ok(favoriteRecipes);
    }
}
