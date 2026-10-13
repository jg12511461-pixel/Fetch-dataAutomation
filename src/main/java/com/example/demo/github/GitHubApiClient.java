package com.example.demo.github;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GitHubApiClient {

    private final RestTemplate restTemplate;

    private final String baseUrl =
            "https://api.github.com/repos/jg12511461-pixel/mpp-task-manager";

    public GitHubApiClient() {
        this.restTemplate = new RestTemplate();
    }

    public Map<String, Object> getRepository() {
        return restTemplate.getForObject(baseUrl, Map.class);
    }

    public List<Map<String, Object>> getBranches() {
        return restTemplate.getForObject(
                baseUrl + "/branches",
                List.class
        );
    }

    public List<Map<String, Object>> getCommits() {
        return restTemplate.getForObject(
                baseUrl + "/commits",
                List.class
        );
    }

    public Map<String, Object> getCommit(String sha) {
        return restTemplate.getForObject(
                baseUrl + "/commits/" + sha,
                Map.class
        );
    }

    public List<Map<String, Object>> getContributors() {
        return restTemplate.getForObject(
                baseUrl + "/contributors",
                List.class
        );
    }

    public Map<String, Object> getUser(String username) {
        return restTemplate.getForObject(
                "https://api.github.com/users/" + username,
                Map.class
        );
    }

    public List<Map<String, Object>> getPullRequests() {
        return restTemplate.getForObject(
                baseUrl + "/pulls?state=all",
                List.class
        );
    }

    public List<Map<String, Object>> getPullRequestCommits(
            Integer pullNumber) {

        return restTemplate.getForObject(
                baseUrl + "/pulls/" + pullNumber + "/commits",
                List.class
        );
    }

    public List<Map<String, Object>> getPullRequestReviews(
            Integer pullNumber) {

        return restTemplate.getForObject(
                baseUrl + "/pulls/" + pullNumber + "/reviews",
                List.class
        );
    }

    public List<Map<String, Object>> getPullRequestComments(
            Integer pullNumber) {

        return restTemplate.getForObject(
                baseUrl + "/pulls/" + pullNumber + "/comments",
                List.class
        );
    }

    public List<Map<String, Object>> getIssues() {
        return restTemplate.getForObject(
                baseUrl + "/issues?state=all",
                List.class
        );
    }

    public List<Map<String, Object>> getIssueComments() {
        return restTemplate.getForObject(
                baseUrl + "/issues/comments",
                List.class
        );
    }

    public List<Map<String, Object>> getEvents() {
        return restTemplate.getForObject(
                baseUrl + "/events",
                List.class
        );
    }
}