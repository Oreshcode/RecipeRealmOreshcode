package com.RecipeRealmOreshcode.services.impl;

import com.RecipeRealmOreshcode.advice.exception.RecordNotFoundException;
import com.RecipeRealmOreshcode.dtos.CommentDto;
import com.RecipeRealmOreshcode.entities.Comment;
import com.RecipeRealmOreshcode.entities.Recipe;
import com.RecipeRealmOreshcode.entities.User;
import com.RecipeRealmOreshcode.repositories.CommentRepository;
import com.RecipeRealmOreshcode.repositories.RecipeRepository;
import com.RecipeRealmOreshcode.repositories.UserRepository;
import com.RecipeRealmOreshcode.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    @Override
    public Comment addComment(Long recipeId, CommentDto commentDto, String authorUsername) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecordNotFoundException("Recipe not found"));
        User author = userRepository.findByUsername(authorUsername)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));

        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        comment.setAuthor(author);
        comment.setCreatedAt(Instant.now());
        comment.setRecipe(recipe);

        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Comment not found"));
        commentRepository.deleteById(id);
    }

    @Override
    public void likeComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Comment not found"));

        comment.setLikes(comment.getLikes() + 1);
        commentRepository.save(comment);
    }

    @Override
    public void dislikeComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Comment not found"));

        comment.setDislikes(comment.getDislikes() + 1);
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByRecipeId(Long recipeId) {
        return commentRepository.findByRecipeId(recipeId);
    }
}
