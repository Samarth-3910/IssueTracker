package com.example.issuetracker.service;

import com.example.issuetracker.model.Issue;
import com.example.issuetracker.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueService {

    private final IssueRepository issueRepository;

    @Autowired
    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    // Existing method
    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    // 🌟 NEW METHOD GOES HERE 🌟
    public List<Issue> searchIssues(String keyword) {
        // If the user typed something, search for it. If the box is empty, return everything.
        if (keyword != null && !keyword.isEmpty()) {
            return issueRepository.findByTitleContainingIgnoreCase(keyword);
        }
        return issueRepository.findAll();
    }

    // Existing method
    public void saveIssue(Issue issue) {
        issueRepository.save(issue);
    }

    // Existing method
    public Issue getIssueById(Long id) {
        return issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue not found for ID: " + id));
    }

    // Existing method
    public void deleteIssue(Long id) {
        issueRepository.deleteById(id);
    }
}
