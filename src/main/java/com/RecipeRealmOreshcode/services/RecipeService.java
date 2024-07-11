package com.RecipeRealmOreshcode.services;

import com.RecipeRealmOreshcode.dtos.RecipeDto;
import com.RecipeRealmOreshcode.entities.Recipe;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

public interface RecipeService {
    @Operation(summary = "Creates a new Recipe", description = "Add a new recipe to the DB using recipeDto and Username")
    Recipe createRecipe(RecipeDto recipeDto, String authorUsername);

    @Operation(summary = "Updates an existing Recipe", description = "Updates the information on a recipe using the Reicpe ID and recipeDTO")
    Recipe updateRecipe(Long id, RecipeDto recipeDto);

    @Operation(summary = "Deletes an Recipe", description = "Deletes an recipe by it's ID.")
    void deleteRecipe(Long id);

    @Operation(summary = "Get recipe by ID", description = "Retrieves an recipe by their ID.")
    Recipe getRecipeById(Long id);

    @Operation(summary = "Searching/Filtering amongst existing recipes.", description = "Retrieves a List of Recipes when both the keyword and the category fields match an existing Recipe.")
    List<Recipe> searchRecipes(String keyword, String category);

    @Operation(summary = "Add a Like to a Recipe", description = "Adds a like to the like counter on a recipe.")
    void likeRecipe(Long id);

    @Operation(summary = "Add a Dislike to a Recipe", description = "Adds a dislike to the like counter on a recipe.")
    void dislikeRecipe(Long id);

    @Operation(summary = "Get all recipes by user ID", description = "Retrieves a List of Recipes added by a person found by their ID.")
    List<Recipe> getRecipesByUser(Long id);
}
