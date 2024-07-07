package com.RecipeRealmOreshcode.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "User ID", example = "1L", required = true)
    private Long id;

    @Column(nullable = false, unique = true)
    @Schema(name = "Username", example = "CuriousGeorge", required = true)
    private String username;

    @Column(nullable = false)
    @Schema(name = "Password", example = "StrongPassword1!", required = true)
    private String password;

    @Column(nullable = false, unique = true)
    @Email
    @Schema(name = "Email address", example = "email@gmail.com", required = true)
    private String email;

    @Schema(name = "Profile Picture URL", example = "https://myprofilepicture.com", required = false)
    private String profilePicture;

    @Schema(name = "Date and Time of User Creation")
    private Instant createdAt;

    @OneToMany(mappedBy = "author")
    @Schema(name = "Recipes By the user")
    private Set<Recipe> recipes;

    @ManyToMany
    @JoinTable(
            name = "user_favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "favorite_id")
    )
    private Set<Favorite> favorites = new HashSet<>();
}
