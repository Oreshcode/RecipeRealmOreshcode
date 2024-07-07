package com.RecipeRealmOreshcode.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "favorites")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "Favorite ID", example = "1L", required = true)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "favorite_users_recipes",
            joinColumns = @JoinColumn(name = "favorite_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    @Schema(name = "Recipes that were added to favorites")
    private Set<Recipe> recipes = new HashSet<>();

    @ManyToMany(mappedBy = "favorites")
    @Schema(name = "Users that added a recipe to favorites")
    private Set<User> users = new HashSet<>();
}
