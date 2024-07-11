package com.RecipeRealmOreshcode.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginRequest {
    @Schema(description = "Username of the user", example = "Jonathan_Joestar")
    private String username;
    @Schema(description = "Password of the user", example = "MyStrongPassword")
    private String password;
}
