package com.RecipeRealmOreshcode.services.impl;

import com.RecipeRealmOreshcode.entities.Favorite;
import com.RecipeRealmOreshcode.entities.Recipe;
import com.RecipeRealmOreshcode.entities.User;
import com.RecipeRealmOreshcode.repositories.FavoriteRepository;
import com.RecipeRealmOreshcode.repositories.RecipeRepository;
import com.RecipeRealmOreshcode.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FavoriteServiceImplTest {

    @InjectMocks
    private FavoriteServiceImpl favoriteService;

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserRepository userRepository;

    private User mockUser;
    private Recipe mockRecipe;
    private Favorite mockFavorite;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testuser");

        mockRecipe = new Recipe();
        mockRecipe.setId(1L);
        mockRecipe.setTitle("Test Recipe");

        mockFavorite = new Favorite();
        mockFavorite.setId(1L);
        mockFavorite.getUsers().add(mockUser);
        mockFavorite.getRecipes().add(mockRecipe);
    }

    @Test
    void testAddFavorite() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(mockUser));
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(mockRecipe));
        when(favoriteRepository.save(any(Favorite.class))).thenReturn(mockFavorite);

        favoriteService.addRecipeToFavorites(mockRecipe.getId(), mockUser.getUsername());

        verify(userRepository, times(1)).findByUsername(anyString());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(favoriteRepository, times(1)).save(any(Favorite.class));
    }

    @Test
    void testRemoveFavorite() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(mockUser));
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(mockRecipe));
        when(favoriteRepository.findByUsersContaining(mockUser)).thenReturn(Optional.of(mockFavorite));

        favoriteService.removeRecipeFromFavorites(mockRecipe.getId(), mockUser.getUsername());

        verify(userRepository, times(1)).findByUsername(anyString());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(favoriteRepository, times(1)).findByUsersContaining(any(User.class));
        verify(favoriteRepository, times(1)).save(any(Favorite.class));
    }

    @Test
    void testGetFavoritesByUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(mockUser));
        when(favoriteRepository.findByUsersContaining(any(User.class))).thenReturn(Optional.of(mockFavorite));

        List<Recipe> favoriteRecipes = favoriteService.getFavoriteRecipesByUsername(mockUser.getUsername());

        assertNotNull(favoriteRecipes);
        assertFalse(favoriteRecipes.isEmpty());
        assertEquals(1, favoriteRecipes.size());
        assertEquals(mockRecipe.getId(), favoriteRecipes.get(0).getId());

        verify(userRepository, times(1)).findByUsername(anyString());
        verify(favoriteRepository, times(1)).findByUsersContaining(any(User.class));
    }

}