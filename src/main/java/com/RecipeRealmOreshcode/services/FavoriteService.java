package com.RecipeRealmOreshcode.services;

import com.RecipeRealmOreshcode.entities.Recipe;

import java.util.List;

public interface FavoriteService {
    void addRecipeToFavorites(Long recipeId, String username);
    void removeRecipeFromFavorites(Long recipeId, String username);
    List<Recipe> getFavoriteRecipesByUsername(String username);
}
