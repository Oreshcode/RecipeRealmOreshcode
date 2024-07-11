package com.RecipeRealmOreshcode.services.impl;

import com.RecipeRealmOreshcode.dtos.UserDto;
import com.RecipeRealmOreshcode.dtos.UserRegistrationDto;
import com.RecipeRealmOreshcode.entities.User;
import com.RecipeRealmOreshcode.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testuser");
        mockUser.setPassword("password");
        mockUser.setEmail("test@example.com");
        mockUser.setProfilePicture("http://testerpiclink.com/test.jpg");
        mockUser.setCreatedAt(Instant.now());
    }

    @Test
    void testLogin() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        User loggedInUser = userService.login("testuser", "password");

        assertNotNull(loggedInUser);
        assertEquals("testuser", loggedInUser.getUsername());

        verify(userRepository, times(1)).findByUsername(anyString());
        verify(passwordEncoder, times(1)).matches(anyString(), anyString());
    }

    @Test
    void testRegisterUser() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setUsername("Testerino");
        userRegistrationDto.setPassword("TesterinoPass");
        userRegistrationDto.setEmail("Tester@test.com");

        User user = new User();
        user.setUsername("Testerino");
        user.setEmail("Tester@test.com");

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = userService.registerUser(userRegistrationDto);

        assertNotNull(registeredUser);
        assertEquals("Testerino", registeredUser.getUsername());
        assertEquals("Tester@test.com", registeredUser.getEmail());

        verify(userRepository, times(1)).save(any(User.class));


    }

    @Test
    void testUpdateUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        User loggedInUser = userService.login("testuser", "password");

        assertNotNull(loggedInUser);
        assertEquals("testuser", loggedInUser.getUsername());

        verify(userRepository, times(1)).findByUsername(anyString());
        verify(passwordEncoder, times(1)).matches(anyString(), anyString());
    }

    @Test
    void testDeleteUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUser));
        doNothing().when(userRepository).deleteById(anyLong());

        userService.deleteUser(mockUser.getId());

        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testGetUserByUsername() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(mockUser));

        Optional<User> foundUser = userService.getUserByUsername("testuser");

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.get().getUsername());

        verify(userRepository, times(1)).findByUsername(anyString());
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUser));

        Optional<User> foundUser = userService.getUserById(mockUser.getId());

        assertNotNull(foundUser);
        assertEquals(mockUser.getId(), foundUser.get().getId());

        verify(userRepository, times(1)).findById(anyLong());
    }
}