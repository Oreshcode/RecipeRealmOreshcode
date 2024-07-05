package com.RecipeRealmOreshcode.controllers;

import com.RecipeRealmOreshcode.dtos.CommentDto;
import com.RecipeRealmOreshcode.entities.Comment;
import com.RecipeRealmOreshcode.services.CommentService;
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
    public ResponseEntity<Comment> addComment(@PathVariable Long recipeId, @RequestBody @Valid CommentDto commentDto, Principal principal) {
        Comment createdComment = commentService.addComment(recipeId, commentDto, principal.getName());
        return ResponseEntity.created(URI.create("/api/comments/" + createdComment.getId())).body(createdComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> likeComment(@PathVariable Long id) {
        commentService.likeComment(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/dislike")
    public ResponseEntity<Void> dislikeComment(@PathVariable Long id) {
        commentService.dislikeComment(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<List<Comment>> getCommentsByRecipeId(@PathVariable Long recipeId) {
        List<Comment> comments = commentService.getCommentsByRecipeId(recipeId);
        return ResponseEntity.ok(comments);
    }
}
