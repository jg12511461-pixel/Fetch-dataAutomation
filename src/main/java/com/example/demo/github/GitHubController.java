package com.example.demo.github;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitHubController {

    private final GitHubService gitHubService;

    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/github/sync")
    public String syncGitHubData() {

        gitHubService.syncAll();

        return "GitHub data synchronized successfully.";
    }
}