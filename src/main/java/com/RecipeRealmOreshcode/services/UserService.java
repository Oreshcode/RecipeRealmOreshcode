package com.RecipeRealmOreshcode.services;

import com.RecipeRealmOreshcode.dtos.UserDto;
import com.RecipeRealmOreshcode.dtos.UserRegistrationDto;
import com.RecipeRealmOreshcode.entities.User;
import io.swagger.v3.oas.annotations.Operation;

import java.util.Optional;

public interface UserService {
    @Operation(summary = "Register a new user", description = "Creates a new user in the database.")
    User registerUser(UserRegistrationDto userRegistrationDto);

    @Operation(summary = "Get user by ID", description = "Retrieves an user by their ID.")
    Optional<User> getUserById(Long id);

    @Operation(summary = "Get user by Username", description = "Retrieves an user by their username.")
    Optional<User> getUserByUsername(String username);

    @Operation(summary = "Deletes an User by ID", description = "Deletes an user from the database by their ID")
    void deleteUser(Long id);

    @Operation(summary = "Updating User Information", description = "Updates an existing User's account information using UserDTO and username.")
    User updateUser(UserDto userDto, String username);

    @Operation(summary = "Logs in an Existing User", description = "Login a user using username and password")
    User login(String username, String password);
}
