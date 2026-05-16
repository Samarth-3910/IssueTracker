package com.example.issuetracker.controller;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.IOException;
import com.example.issuetracker.model.Issue;
import com.example.issuetracker.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
public class IssueController {
    private final IssueService issueService;
    @Autowired
    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }
    // 1. Show all issues on the Homepage
    @GetMapping("/")
    public String viewHomePage(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        // We use our new search method instead of getAllIssues()
        model.addAttribute("listIssues", issueService.searchIssues(keyword));

        // We send the keyword back to the HTML so the search box doesn't empty itself after clicking search
        model.addAttribute("keyword", keyword);

        return "list";
    }
    // 2. Show the "Create New Issue" form
    @GetMapping("/new")
    public String showNewIssueForm(Model model) {
        Issue issue = new Issue();
        model.addAttribute("issue", issue);
        return "create"; // Looks for "create.html"
    }
    // 3. Save the issue when the form is submitted
    @PostMapping("/save")
    public String saveIssue(@Valid @ModelAttribute("issue") Issue issue, BindingResult result,
                            @RequestParam("image") MultipartFile multipartFile) throws IOException {

        if (result.hasErrors()) { return "create"; }

        // If the user uploaded a file
        if (!multipartFile.isEmpty()) {
            // Get the filename (e.g., "error.png")
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            issue.setScreenshotFilename(fileName);

            // Save the issue to the Database
            issueService.saveIssue(issue);

            // Save the actual image file to the computer
            String uploadDir = "uploads/";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath); // Create folder if it doesn't exist
            }

            try (InputStream inputStream = multipartFile.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }
        } else {
            // If no file was uploaded, just save normally
            issueService.saveIssue(issue);
        }

        return "redirect:/";
    }
    // 4. Delete an issue by its ID
    @GetMapping("/delete/{id}")
    public String deleteIssue(@PathVariable(value = "id") Long id) {
        issueService.deleteIssue(id);
        return "redirect:/"; // Redirects back to the homepage
    }
    // 5. Show the Details Page for a specific Issue
    @GetMapping("/issue/{id}")
    public String viewIssueDetails(@PathVariable(value = "id") Long id, Model model) {
        // Grab the specific issue from the database
        Issue issue = issueService.getIssueById(id);
        model.addAttribute("issue", issue);
        return "details"; // We will create details.html next
    }

    // 6. Handle the Status Update from the Details Page
    @PostMapping("/updateStatus/{id}")
    public String updateIssueStatus(@PathVariable(value = "id") Long id, @RequestParam("status") String status) {
        // 1. Get the existing issue
        Issue issue = issueService.getIssueById(id);

        // 2. Change its status
        issue.setStatus(status);

        // 3. Save it back to the database
        issueService.saveIssue(issue);

        // 4. Redirect back to the details page so they can see the change
        return "redirect:/issue/" + id;
    }
}
