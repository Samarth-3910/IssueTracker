package com.example.issuetracker.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "issues")
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title cannot be empty!")
    @Column(nullable = false)
    private String title;
    @NotBlank(message = "Description is required!")
    @Size(min = 10, message = "Description must be at least 10 characters long.")
    @Column(length = 1000)
    private String description;
    @Column(nullable = false)
    private String status = "Open"; // e.g., "Open", "In Progress", "Closed"
    @Column(nullable = false)
    private String category;
    // Empty constructor needed by Spring Boot
    public Issue() {
    }
    public Issue(String title, String description, String status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }
    private String screenshotFilename;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // --- GETTERS AND SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public String getScreenshotFilename() { return screenshotFilename; }
    public void setScreenshotFilename(String screenshotFilename) { this.screenshotFilename = screenshotFilename; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
