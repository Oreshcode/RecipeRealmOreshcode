package com.RecipeRealmOreshcode.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "Recipe ID", example = "1L", required = true)
    private Long id;

    @Column(nullable = false)
    @Schema(name = "Recipe Title", example = "My New Recipe", required = true)
    private String title;

    @Column(nullable = false)
    @Schema(name = "Recipe Description", example = "New Recipe's Description", required = true)
    private String description;

    @Column(nullable = false)
    @Schema(name = "Recipe Ingredients", example = "Required Ingredients", required = true)
    private String ingredients;

    @Column(nullable = false)
    @Schema(name = "Preparation instructions", example = "How to prepare the dish", required = true)
    private String instructions;

    @Column(nullable = false)
    @Schema(name = "Recipe Category", example = "Breakfast, Dinner, Fast Food, etc.", required = true)
    private String category;

    @Schema(name = "Recipe Date and Time of creation", required = true)
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Schema(name = "Likes for a recipe")
    private Long likes = 0L;

    @Schema(name = "Dislikes for a recipe")
    private Long dislikes = 0L;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(name = "Recipe Comments")
    private Set<Comment> comments = new HashSet<>();
}
