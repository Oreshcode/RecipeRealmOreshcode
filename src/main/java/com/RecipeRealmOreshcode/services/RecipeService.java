package com.RecipeRealmOreshcode.services;

import com.RecipeRealmOreshcode.dtos.RecipeDto;
import com.RecipeRealmOreshcode.entities.Recipe;

import java.util.List;

public interface RecipeService {
    Recipe createRecipe(RecipeDto recipeDto, String authorUsername);
    Recipe updateRecipe(Long id, RecipeDto recipeDto);
    void deleteRecipe(Long id);
    Recipe getRecipeById(Long id);
    List<Recipe> searchRecipes(String keyword, String category);
    void likeRecipe(Long id);
    void dislikeRecipe(Long id);
    List<Recipe> getRecipesByUser(String username);
}
