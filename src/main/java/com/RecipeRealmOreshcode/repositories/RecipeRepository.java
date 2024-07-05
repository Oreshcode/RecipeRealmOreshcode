package com.RecipeRealmOreshcode.repositories;

import com.RecipeRealmOreshcode.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByAuthorId(Long authorId);
    List<Recipe> findByCategory(String category);
    List<Recipe> findByTitleContaining(String keyword);
}
