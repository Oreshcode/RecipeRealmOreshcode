package com.RecipeRealmOreshcode.services.impl;

import com.RecipeRealmOreshcode.dtos.RecipeDto;
import com.RecipeRealmOreshcode.entities.Recipe;
import com.RecipeRealmOreshcode.entities.User;
import com.RecipeRealmOreshcode.repositories.RecipeRepository;
import com.RecipeRealmOreshcode.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {
    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    private User user;
    private RecipeDto recipeDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        recipeDto = new RecipeDto();
        recipeDto.setTitle("Test Recipe");
        recipeDto.setDescription("Test Description");
        recipeDto.setIngredients("Test Ingredients");
        recipeDto.setInstructions("Test Instructions");
        recipeDto.setCategory("Test Category");
    }

    @Test
    void createRecipe() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(new Recipe());

        Recipe recipe = recipeService.createRecipe(recipeDto, user.getUsername());

        assertNotNull(recipe);
        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void updateRecipe() {
    }

    @Test
    void deleteRecipe() {
    }

    @Test
    void getRecipeById() {
    }

    @Test
    void searchRecipes() {
    }

    @Test
    void likeRecipe() {
    }

    @Test
    void dislikeRecipe() {
    }
}