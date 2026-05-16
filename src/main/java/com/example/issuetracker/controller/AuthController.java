package com.example.issuetracker.controller;

import com.example.issuetracker.model.User;
import com.example.issuetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Show the Login Page
    @GetMapping("/login")
    public String viewLoginPage() {
        return "login";
    }

    // Show the Registration Page
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    // Handle the Registration Form Submission
    @PostMapping("/register")
    public String processRegister(@ModelAttribute User user) {
        // 1. NEVER save a plain text password! We encrypt it using BCrypt.
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // 2. Everyone who registers is a normal USER. Only developers get ADMIN.
        user.setRole("USER");

        // 3. Save them to MySQL
        userRepository.save(user);

        // 4. Redirect them to the login page so they can sign in!
        return "redirect:/login?registered";
    }
}
