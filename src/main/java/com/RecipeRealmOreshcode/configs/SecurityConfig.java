package com.RecipeRealmOreshcode.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**",
                                "/api/users/login", "/api/users/register", "/api/users/update", "/api/users/{id}", "/api/users/me",
                                "/api/recipes/create", "/api/recipes/recipes", "/api/recipes/{id}", "/api/recipes/{id}/like", "/api/recipes/{id}/dislike",
                                "/api/recipes/user/{id}", "/api/favorites/add/{recipeId}", "/api/favorites/remove/{recipeId}", "/api/favorites/recipes",
                                "/api/comments/recipe/{recipeId}", "/api/comments/{id}", "/api/comments/{id}/like", "/api/comments/{id}/dislike", "/api/comments/recipe/{recipeId}"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/api/auth/login")
                        .permitAll()
                )
                .httpBasic(withDefaults());
        return http.build();
    }
}
