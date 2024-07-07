package com.RecipeRealmOreshcode.services;

import com.RecipeRealmOreshcode.dtos.CommentDto;
import com.RecipeRealmOreshcode.entities.Comment;

import java.util.List;

public interface CommentService {
    Comment addComment(Long recipeId, CommentDto commentDto, String authorUsername);
    void deleteComment(Long id);
    void likeComment(Long id);
    void dislikeComment(Long id);
    List<Comment> getCommentsByRecipeId(Long recipeId);
}
