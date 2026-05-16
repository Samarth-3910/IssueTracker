# Issue Support Portal

A professional, enterprise-grade issue tracking web application built with a custom gaming-centric UI/UX. Features a fully-fledged Role-Based Access Control (RBAC) system, secure file uploads, and a dynamic frontend.

## Tech Stack

*   **Backend:** Java 26, Spring Boot, Spring Web
*   **Security:** Spring Security (RBAC), BCrypt Password Hashing
*   **Database:** MySQL, Spring Data JPA (Hibernate)
*   **Frontend:** Thymeleaf, HTML5, Vanilla CSS, Bootstrap 5
*   **Architecture:** MVC (Model-View-Controller)

## Installation Guide

### Prerequisites
*   Java Development Kit (JDK) 17+
*   Maven
*   MySQL Server (Running on `localhost:3306`)

### 1. Database Setup
Create an empty database in MySQL:
```sql
CREATE DATABASE issue_tracker_db;
```

### 2. Application Configuration
Update the `src/main/resources/application.properties` with your MySQL credentials:
```properties
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3. Run the Application
Navigate to the project root and run the Spring Boot application using Maven:
```bash
mvn spring-boot:run
```

### 4. Default Access
The application will be accessible at `http://localhost:8080`.
*   **Register** a new account to test the standard `USER` role functionalities.
*   To test the `ADMIN` role (which unlocks status management), update a user's role directly in the MySQL database:
    ```sql
    UPDATE users SET role = 'ADMIN' WHERE username = 'your_username';
    ```
