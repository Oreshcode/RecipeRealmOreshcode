package com.RecipeRealmOreshcode.repositories;

import com.RecipeRealmOreshcode.entities.Favorite;
import com.RecipeRealmOreshcode.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByUsersContaining(User user);
}
