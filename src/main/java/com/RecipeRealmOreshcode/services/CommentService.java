package com.RecipeRealmOreshcode.services;

import com.RecipeRealmOreshcode.dtos.CommentDto;
import com.RecipeRealmOreshcode.entities.Comment;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

public interface CommentService {
    @Operation(summary = "Add a new Comment", description = "Add a new comment to a Recipe, using recipe id, commentDTO and Username")
    Comment addComment(Long recipeId, CommentDto commentDto, String authorUsername);

    @Operation(summary = "Delete a Comment", description = "Deletes a comment using the comment id")
    void deleteComment(Long id);

    @Operation(summary = "Add a Like to a Comment", description = "Adds a like to the like counter on a comment using comment id")
    void likeComment(Long id);

    @Operation(summary = "Add a dislike to a comment", description = "Adds a dislike to the dislike counter on a comment using comment id")
    void dislikeComment(Long id);

    @Operation(summary = "Retrieves all Comments on a Recipe", description = "Retrieves a List of Comments on a Recipe")
    List<Comment> getCommentsByRecipeId(Long recipeId);
}
