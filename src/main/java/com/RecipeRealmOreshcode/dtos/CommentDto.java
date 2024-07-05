package com.RecipeRealmOreshcode.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentDto {
    @NotBlank
    private String text;
}
