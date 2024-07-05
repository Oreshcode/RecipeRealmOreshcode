package com.RecipeRealmOreshcode.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RecipeDto {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String ingredients;

    @NotBlank
    private String instructions;

    @NotBlank
    private String category;
}
