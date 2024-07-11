package com.RecipeRealmOreshcode.controllers;

import com.RecipeRealmOreshcode.dtos.CommentDto;
import com.RecipeRealmOreshcode.entities.Comment;
import com.RecipeRealmOreshcode.services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/recipe/{recipeId}")
    @Operation(summary = "Adds a new Comment to a Recipe", description = "Adds a new comment to a Recipe using Recipe ID and User information.")
    public ResponseEntity<Comment> addComment(@PathVariable Long recipeId, @RequestBody @Valid CommentDto commentDto, Principal principal) {
        Comment createdComment = commentService.addComment(recipeId, commentDto, principal.getName());
        return ResponseEntity.created(URI.create("/api/comments/" + createdComment.getId())).body(createdComment);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Removes a Comment", description = "Removes an existing comment using the commentId")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/like")
    @Operation(summary = "Add a like to a comment", description = "Adds a like to an existing comment using it's id")
    public ResponseEntity<Void> likeComment(@PathVariable Long id) {
        commentService.likeComment(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/dislike")
    @Operation(summary = "Add a dislike to a comment", description = "Adds a dislike to an existing comment using it's id")
    public ResponseEntity<Void> dislikeComment(@PathVariable Long id) {
        commentService.dislikeComment(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recipe/{recipeId}")
    @Operation(summary = "Retrieves all Comments on a Recipe", description = "Retrieves all Comments that exist on a Recipe using it's id.")
    public ResponseEntity<List<Comment>> getCommentsByRecipeId(@PathVariable Long recipeId) {
        List<Comment> comments = commentService.getCommentsByRecipeId(recipeId);
        return ResponseEntity.ok(comments);
    }
}
