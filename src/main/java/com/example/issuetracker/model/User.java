package com.example.issuetracker.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // Renaming the table because 'user' is a reserved keyword in some SQL databases
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password; // This will hold the scrambled BCrypt hash, never plain text!

    @Column(nullable = false)
    private String role; // e.g., "USER" or "ADMIN"

    // Default constructor
    public User() {}

    // --- GETTERS AND SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
