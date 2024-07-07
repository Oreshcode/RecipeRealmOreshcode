package com.RecipeRealmOreshcode.services.impl;

import com.RecipeRealmOreshcode.dtos.CommentDto;
import com.RecipeRealmOreshcode.entities.Comment;
import com.RecipeRealmOreshcode.entities.Recipe;
import com.RecipeRealmOreshcode.entities.User;
import com.RecipeRealmOreshcode.repositories.CommentRepository;
import com.RecipeRealmOreshcode.repositories.RecipeRepository;
import com.RecipeRealmOreshcode.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CommentServiceImplTest {
    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddComment() {
        User user = new User();
        user.setId(1L);
        user.setUsername("CommentTester");

        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setTitle("Test Recipe Title");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        CommentDto commentDto = new CommentDto();
        commentDto.setText("Test Comment");

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText("Test Comment");
        comment.setAuthor(user);
        comment.setRecipe(recipe);
        comment.setCreatedAt(Instant.now());

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        Comment createdComment = commentService.addComment(recipe.getId(), commentDto, user.getUsername());

        assertNotNull(createdComment);
        assertEquals("Test Comment", createdComment.getText());
        assertEquals(user, createdComment.getAuthor());
        assertEquals(recipe, createdComment.getRecipe());

        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void testDeleteComment() {
        Comment comment = new Comment();
        comment.setId(1L);
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));

        commentService.deleteComment(1L);

        verify(commentRepository, times(1)).deleteById(1L);
    }

    @Test
    void testLikeComment() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setLikes(0L);
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        commentService.likeComment(1L);

        assertEquals(1L, comment.getLikes());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void testDislikeComment() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setDislikes(0L);
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        commentService.dislikeComment(1L);

        assertEquals(1L, comment.getDislikes());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void testGetCommentsByRecipeId() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Comment comment1 = new Comment();
        comment1.setId(1L);
        comment1.setRecipe(recipe);

        Comment comment2 = new Comment();
        comment2.setId(2L);
        comment2.setRecipe(recipe);

        List<Comment> comments = new ArrayList<>();
        comments.add(comment1);
        comments.add(comment2);

        when(commentRepository.findByRecipeId(anyLong())).thenReturn(comments);

        List<Comment> foundComments = commentService.getCommentsByRecipeId(1L);

        assertNotNull(foundComments);
        assertEquals(2, foundComments.size());
    }
}