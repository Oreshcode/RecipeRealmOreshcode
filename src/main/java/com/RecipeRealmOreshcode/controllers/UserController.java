package com.RecipeRealmOreshcode.controllers;

import com.RecipeRealmOreshcode.dtos.LoginRequest;
import com.RecipeRealmOreshcode.dtos.UserDto;
import com.RecipeRealmOreshcode.dtos.UserRegistrationDto;
import com.RecipeRealmOreshcode.entities.User;
import com.RecipeRealmOreshcode.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user in the database.")
    public ResponseEntity<User> registerUser(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        User registeredUser = userService.registerUser(userRegistrationDto);
        return ResponseEntity.created(URI.create("/api/users/" + registeredUser.getId())).body(registeredUser);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves an user by their ID.")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/me")
    @Operation(summary = "Get already logged in User.", description = "Retrieves an User that has been logged in.")
    public ResponseEntity<User> getLoggedUser(Principal principal) {
        Optional<User> user = userService.getUserByUsername(principal.getName());
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes an User by ID", description = "Deletes an user from the database.")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    @Operation(summary = "Logs in an User using Username and Password", description = "Login.")
    public ResponseEntity<User> login(@RequestBody @Valid LoginRequest loginRequest) {
        User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update")
    @Operation(summary = "Updating User Information", description = "Updates an existing User's account information.")
    public ResponseEntity<User> updateUser(@RequestBody @Valid UserDto userDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User updatedUser = userService.updateUser(userDto, currentUsername);
        return ResponseEntity.ok(updatedUser);
    }
}
