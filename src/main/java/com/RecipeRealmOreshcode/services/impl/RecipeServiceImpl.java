package com.RecipeRealmOreshcode.services.impl;

import com.RecipeRealmOreshcode.advice.exception.RecordNotFoundException;
import com.RecipeRealmOreshcode.dtos.RecipeDto;
import com.RecipeRealmOreshcode.entities.Recipe;
import com.RecipeRealmOreshcode.entities.User;
import com.RecipeRealmOreshcode.repositories.RecipeRepository;
import com.RecipeRealmOreshcode.repositories.UserRepository;
import com.RecipeRealmOreshcode.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    @Override
    public Recipe createRecipe(RecipeDto recipeDto, String authorUsername) {
        User author = userRepository.findByUsername(authorUsername)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));

        Recipe recipe = new Recipe();
        recipe.setTitle(recipeDto.getTitle());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setIngredients(recipeDto.getIngredients());
        recipe.setInstructions(recipeDto.getInstructions());
        recipe.setCategory(recipeDto.getCategory());
        recipe.setCreatedAt(Instant.now());
        recipe.setAuthor(author);

        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe updateRecipe(Long id, RecipeDto recipeDto) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Recipe not found"));

        recipe.setTitle(recipeDto.getTitle());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setIngredients(recipeDto.getIngredients());
        recipe.setInstructions(recipeDto.getInstructions());
        recipe.setCategory(recipeDto.getCategory());

        return recipeRepository.save(recipe);
    }

    @Override
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Recipe not found"));
    }

    @Override
    public List<Recipe> searchRecipes(String keyword, String category) {
        if (category != null) {
            return recipeRepository.findByCategory(category);
        }
        return recipeRepository.findByTitleContaining(keyword);
    }

    @Override
    public void likeRecipe(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Recipe not found"));

        recipe.setLikes(recipe.getLikes() + 1);
        recipeRepository.save(recipe);
    }

    @Override
    public void dislikeRecipe(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Recipe not found"));

        recipe.setDislikes(recipe.getDislikes() + 1);
        recipeRepository.save(recipe);
    }

    @Override
    public List<Recipe> getRecipesByUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
        return recipeRepository.findByAuthorId(user.getId());
    }
}
