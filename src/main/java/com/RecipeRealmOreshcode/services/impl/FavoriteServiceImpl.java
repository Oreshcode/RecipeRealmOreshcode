package com.RecipeRealmOreshcode.services.impl;

import com.RecipeRealmOreshcode.entities.Favorite;
import com.RecipeRealmOreshcode.entities.Recipe;
import com.RecipeRealmOreshcode.entities.User;
import com.RecipeRealmOreshcode.repositories.FavoriteRepository;
import com.RecipeRealmOreshcode.repositories.RecipeRepository;
import com.RecipeRealmOreshcode.repositories.UserRepository;
import com.RecipeRealmOreshcode.services.FavoriteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    @Override
    public void addRecipeToFavorites(Long recipeId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found"));

        Optional<Favorite> favoriteOptional = favoriteRepository.findByUsersContaining(user);
        Favorite favorite;
        if (favoriteOptional.isPresent()) {
            favorite = favoriteOptional.get();
        } else {
            favorite = new Favorite();
            favorite.getUsers().add(user);
            favoriteRepository.save(favorite);
            user.getFavorites().add(favorite);
            userRepository.save(user);
        }

        favorite.getRecipes().add(recipe);
        favoriteRepository.save(favorite);
    }

    @Override
    public void removeRecipeFromFavorites(Long recipeId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found"));

        Favorite favorite = favoriteRepository.findByUsersContaining(user)
                .orElseThrow(() -> new EntityNotFoundException("Favorite list not found"));

        favorite.getRecipes().remove(recipe);
        favoriteRepository.save(favorite);
    }

    @Override
    public Favorite getFavoritesByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return favoriteRepository.findByUsersContaining(user)
                .orElseThrow(() -> new EntityNotFoundException("Favorite list not found"));
    }

    @Override
    public List<Recipe> getFavoriteRecipesByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Optional<Favorite> favoriteOptional = favoriteRepository.findByUsersContaining(user);
        return favoriteOptional.map(favorite -> new ArrayList<>(favorite.getRecipes())).orElseGet(ArrayList::new);
    }
}
