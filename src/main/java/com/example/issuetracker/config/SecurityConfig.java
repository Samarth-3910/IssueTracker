package com.example.issuetracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. Set up the specific URL Rules
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        // Guests (Not logged in) can access the dashboard, view issues, and register
                        .requestMatchers("/", "/css/**", "/uploads/**", "/register", "/login", "/issue/**").permitAll()

                        // Only Gamers (USER) and Admins can report new bugs
                        .requestMatchers("/new", "/save").hasAnyRole("USER", "ADMIN")

                        // ONLY Admins (Riot Support) can update the status or delete issues
                        .requestMatchers("/updateStatus/**", "/delete/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    // 2. The Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // NOTE: Notice we deleted the hardcoded user!
    // Because we created CustomUserDetailsService.java, Spring Boot automatically
    // discovers it and uses our MySQL database for login verification!
}
