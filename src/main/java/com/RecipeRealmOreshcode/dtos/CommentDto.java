package com.RecipeRealmOreshcode.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentDto {
    @NotBlank
    @Schema(description = "A string of text, containing the comment's body")
    private String text;
}
