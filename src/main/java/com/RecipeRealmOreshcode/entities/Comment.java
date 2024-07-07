package com.RecipeRealmOreshcode.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "Comment ID", example = "1L", required = true)
    private Long id;

    @Column(nullable = false)
    @NotNull
    @Schema(name = "Comment Text", example = "My New Comment", required = true)
    private String text;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @Schema(name = "Comment Author", required = true)
    private User author;

    @Schema(name = "Comment Date and Time of adding", required = true)
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    @Schema(name = "Recipe of the comment", required = true)
    private Recipe recipe;

    @Schema(name = "Likes for a comment")
    private Long likes = 0L;

    @Schema(name = "Dislikes for a comment")
    private Long dislikes = 0L;
}