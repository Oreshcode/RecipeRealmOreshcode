package com.RecipeRealmOreshcode.services;

import com.RecipeRealmOreshcode.entities.Recipe;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

public interface FavoriteService {
    @Operation(summary = "Adds a Recipe to Favorite", description = "Adds a Recipe to favorite list using recipeId and Username.")
    void addRecipeToFavorites(Long recipeId, String username);

    @Operation(summary = "Remove a Recipe from Favorites", description = "Removes a Recipe from Favorite using the recipeID and Username.")
    void removeRecipeFromFavorites(Long recipeId, String username);

    @Operation(summary = "Gets all Favorited recipes by an User", description = "Retrieves a List of Recipes that were Favorited by an User, using their username")
    List<Recipe> getFavoriteRecipesByUsername(String username);
}
