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

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {
    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRecipe() {
        User user = new User();
        user.setId(1L);
        user.setUsername("JohnTesty");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setTitle("Test Recipe");
        recipeDto.setDescription("Test Description");
        recipeDto.setIngredients("Test Ingredients");
        recipeDto.setInstructions("Test Instructions");
        recipeDto.setCategory("Test Category");

        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setTitle("Test Recipe");
        recipe.setDescription("Test Description");
        recipe.setIngredients("Test Ingredients");
        recipe.setInstructions("Test Instructions");
        recipe.setCategory("Test Category");
        recipe.setAuthor(user);
        recipe.setCreatedAt(Instant.now());

        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        Recipe createdRecipe = recipeService.createRecipe(recipeDto, user.getUsername());

        assertNotNull(createdRecipe);
        assertEquals("Test Recipe", createdRecipe.getTitle());
        assertEquals("Test Description", createdRecipe.getDescription());

        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void testUpdateRecipe() {
        User user = new User();
        user.setId(1L);
        user.setUsername("JohnTesty");

        Recipe existingRecipe = new Recipe();
        existingRecipe.setId(1L);
        existingRecipe.setTitle("Old Title");
        existingRecipe.setDescription("Old Description");
        existingRecipe.setIngredients("Old Ingredients");
        existingRecipe.setInstructions("Old Instructions");
        existingRecipe.setCategory("Old Category");
        existingRecipe.setAuthor(user);
        existingRecipe.setCreatedAt(Instant.now());

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(existingRecipe));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(existingRecipe);

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setTitle("Updated Title");
        recipeDto.setDescription("Updated Description");
        recipeDto.setIngredients("Updated Ingredients");
        recipeDto.setInstructions("Updated Instructions");
        recipeDto.setCategory("Updated Category");

        Recipe updatedRecipe = recipeService.updateRecipe(1L, recipeDto);

        assertNotNull(updatedRecipe);
        assertEquals("Updated Title", updatedRecipe.getTitle());
        assertEquals("Updated Description", updatedRecipe.getDescription());

        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void testDeleteRecipe() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        recipeService.deleteRecipe(1L);

        verify(recipeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetRecipeById() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        Recipe foundRecipe = recipeService.getRecipeById(1L);

        assertNotNull(foundRecipe);
        assertEquals(1L, foundRecipe.getId());
    }

    @Test
    void testSearchRecipes() {
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        recipe1.setTitle("Recipe 1");
        recipe1.setCategory("Lunch");

        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        recipe2.setTitle("Recipe 2");
        recipe2.setCategory("Dinner");

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);

        when(recipeRepository.findAll()).thenReturn(recipes);

        List<Recipe> foundRecipes = recipeService.searchRecipes("Recipe", "Dinner");

        assertNotNull(foundRecipes);
        assertEquals(1, foundRecipes.size());
    }

    @Test
    void testLikeRecipe() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setLikes(0L);
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        recipeService.likeRecipe(1L);

        assertEquals(1L, recipe.getLikes());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void testDislikeRecipe() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setDislikes(0L);
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        recipeService.dislikeRecipe(1L);

        assertEquals(1L, recipe.getDislikes());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void testGetRecipesByUser() {
        User user = new User();
        user.setId(1L);

        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        recipe1.setAuthor(user);

        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        recipe2.setAuthor(user);

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);

        when(recipeRepository.findByAuthorId(any())).thenReturn(recipes);

        List<Recipe> foundRecipes = recipeService.getRecipesByUser(1L);

        assertNotNull(foundRecipes);
        assertEquals(2, foundRecipes.size());
    }
}