package com.RecipeRealmOreshcode.services.impl;

import com.RecipeRealmOreshcode.dtos.RecipeDto;
import com.RecipeRealmOreshcode.entities.Recipe;
import com.RecipeRealmOreshcode.entities.User;
import com.RecipeRealmOreshcode.repositories.RecipeRepository;
import com.RecipeRealmOreshcode.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {
    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserRepository userRepository;

    private User mockUser;
    private Recipe mockRecipe;
    private RecipeDto mockRecipeDto;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testuser");
        mockUser.setEmail("testemail@test.com");
        mockUser.setPassword("testpassword");

        mockRecipeDto = new RecipeDto();
        mockRecipeDto.setTitle("Test Recipe");
        mockRecipeDto.setDescription("Test Description");
        mockRecipeDto.setIngredients("Test Ingredients");
        mockRecipeDto.setInstructions("Test Instructions");
        mockRecipeDto.setCategory("Test Category");

        mockRecipe = new Recipe();
        mockRecipe.setId(1L);
        mockRecipe.setTitle("Test Recipe");
        mockRecipe.setDescription("Test Description");
        mockRecipe.setIngredients("Test Ingredients");
        mockRecipe.setInstructions("Test Instructions");
        mockRecipe.setCategory("Test Category");
        mockRecipe.setCreatedAt(Instant.now());
        mockRecipe.setAuthor(mockUser);
    }

    @Test
    void testCreateRecipe() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(mockUser));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(mockRecipe);

        Recipe createdRecipe = recipeService.createRecipe(mockRecipeDto, mockUser.getUsername());

        assertNotNull(createdRecipe);
        assertEquals("Test Recipe", createdRecipe.getTitle());
        assertEquals("testuser", createdRecipe.getAuthor().getUsername());

        verify(userRepository, times(1)).findByUsername(anyString());
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
        Long recipeId = 1L;

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(mockRecipe));
        doNothing().when(recipeRepository).deleteById(recipeId);

        recipeService.deleteRecipe(recipeId);

        verify(recipeRepository, times(1)).findById(recipeId);
        verify(recipeRepository, times(1)).deleteById(recipeId);
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
        recipe1.setTitle("Recipe");
        recipe1.setCategory("Lunch");
        recipe1.setDescription("Eggs");

        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        recipe2.setTitle("Recipe");
        recipe2.setCategory("Dinner");
        recipe2.setDescription("Steak");

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);

        when(recipeRepository.findAll()).thenReturn(recipes);

        List<Recipe> foundRecipes = recipeService.searchRecipes("Steak", "Dinner");

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
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(recipeRepository.findByAuthorId(userId)).thenReturn(List.of(mockRecipe));

        List<Recipe> recipes = recipeService.getRecipesByUser(userId);

        assertNotNull(recipes);
        assertFalse(recipes.isEmpty());
        assertEquals("testuser", recipes.get(0).getAuthor().getUsername());

        verify(userRepository, times(1)).findById(userId);
        verify(recipeRepository, times(1)).findByAuthorId(userId);
    }
}