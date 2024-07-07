package com.RecipeRealmOreshcode.services.impl;

import com.RecipeRealmOreshcode.entities.Favorite;
import com.RecipeRealmOreshcode.entities.Recipe;
import com.RecipeRealmOreshcode.entities.User;
import com.RecipeRealmOreshcode.repositories.FavoriteRepository;
import com.RecipeRealmOreshcode.repositories.RecipeRepository;
import com.RecipeRealmOreshcode.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class FavoriteServiceImplTest {

    @InjectMocks
    private FavoriteServiceImpl favoriteService;

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddFavorite() {
        User user = new User();
        user.setId(1L);
        user.setUsername("TestFav");
        user.setEmail("newuser@example.com");
        user.setPassword("1234567");

        Set<User> users = new HashSet<>();
        users.add(user);

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Favorite favorite = new Favorite();
        favorite.setUsers(users);;
        Set<Recipe> recipes = new HashSet<>();
        favorite.setRecipes(recipes);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(favoriteRepository.save(any(Favorite.class))).thenReturn(favorite);

        favoriteService.addRecipeToFavorites(1L, user.getUsername());

        verify(favoriteRepository, times(1)).save(any(Favorite.class));
    }

    @Test
    void testRemoveFavorite() {
        User user = new User();
        user.setId(1L);
        user.setUsername("TestFav");

        Set<User> users = new HashSet<>();
        users.add(user);

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Favorite favorite = new Favorite();
        favorite.setUsers(users);
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(recipe);
        favorite.setRecipes(recipes);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(favoriteRepository.findById(anyLong())).thenReturn(Optional.of(favorite));

        favoriteService.removeRecipeFromFavorites(1L, user.getUsername());

        verify(favoriteRepository, times(1)).save(any(Favorite.class));
        assertFalse(favorite.getRecipes().contains(recipe));
    }

    @Test
    void testGetFavoritesByUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("TestFindFavs");

        Set<User> users = new HashSet<>();
        users.add(user);

        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);

        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);

        Favorite favorite = new Favorite();
        favorite.setUsers(users);
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(recipe1);
        recipes.add(recipe2);
        favorite.setRecipes(recipes);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(favoriteRepository.findByUsersContaining(any(User.class))).thenReturn(Optional.of(favorite));

        List<Recipe> foundFavorites = favoriteService.getFavoriteRecipesByUsername(user.getUsername());

        assertNotNull(foundFavorites);
        assertEquals(2, foundFavorites.size());
    }

}