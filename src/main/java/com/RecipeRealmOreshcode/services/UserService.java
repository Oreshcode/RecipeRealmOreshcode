package com.RecipeRealmOreshcode.services;

import com.RecipeRealmOreshcode.dtos.UserDto;
import com.RecipeRealmOreshcode.dtos.UserRegistrationDto;
import com.RecipeRealmOreshcode.entities.User;

import java.util.Optional;

public interface UserService {
    User registerUser(UserRegistrationDto userRegistrationDto);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByUsername(String username);
    void deleteUser(Long id);
    User updateUser(UserDto userDto, String username);
}
