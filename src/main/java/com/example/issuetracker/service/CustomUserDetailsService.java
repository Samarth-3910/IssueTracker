package com.example.issuetracker.service;

import com.example.issuetracker.model.User;
import com.example.issuetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Find the user in our MySQL database
        User myUser = userRepository.findByUsername(username);

        if (myUser == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // 2. Translate our User into a Spring Security UserDetails object
        return org.springframework.security.core.userdetails.User
                .withUsername(myUser.getUsername())
                .password(myUser.getPassword()) // Spring Security will automatically check if this matches the login attempt
                .roles(myUser.getRole()) // Asserts if they are "USER" or "ADMIN"
                .build();
    }
}
