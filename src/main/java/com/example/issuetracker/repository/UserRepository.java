package com.example.issuetracker.repository;

import com.example.issuetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Boot automatically writes the SQL: SELECT * FROM users WHERE username = ?
    User findByUsername(String username);
}
