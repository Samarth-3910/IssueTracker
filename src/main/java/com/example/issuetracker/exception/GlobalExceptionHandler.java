package com.example.issuetracker.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler {
    // This method intercepts ANY Exception thrown anywhere in the app!
    @ExceptionHandler(Exception.class)
    public String handleGlobalException(Exception ex, Model model) {
        // 1. Log the error in your console so YOU can debug it easily
        System.err.println("CRITICAL ERROR CAPTURED: " + ex.getMessage());
        ex.printStackTrace();
        // 2. Pass the error message to the frontend so the USER sees what went wrong
        model.addAttribute("errorMessage", ex.getMessage());

        // 3. Return a beautifully styled error HTML page (we will build this in Phase 5)
        return "error";
    }
}
