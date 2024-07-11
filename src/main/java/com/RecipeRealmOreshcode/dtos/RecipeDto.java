package com.RecipeRealmOreshcode.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RecipeDto {
    @NotBlank
    @Schema(description = "The title of a Recipe as a String")
    private String title;

    @NotBlank
    @Schema(description = "The description of a Recipe as a String")
    private String description;

    @NotBlank
    @Schema(description = "The ingredients of a Recipe as a String")
    private String ingredients;

    @NotBlank
    @Schema(description = "The instructions of a Recipe as a String")
    private String instructions;

    @NotBlank
    @Schema(description = "The category of a Recipe as a String")
    private String category;
}
