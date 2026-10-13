package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "github_pull_request_commits")
public class GitHubPullRequestCommit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer pullNumber;
    private String commitSha;
    private String username;
    private String commitDate;

    public GitHubPullRequestCommit() {
    }

    public Long getId() {
        return id;
    }

    public Integer getPullNumber() {
        return pullNumber;
    }

    public void setPullNumber(Integer pullNumber) {
        this.pullNumber = pullNumber;
    }

    public String getCommitSha() {
        return commitSha;
    }

    public void setCommitSha(String commitSha) {
        this.commitSha = commitSha;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(String commitDate) {
        this.commitDate = commitDate;
    }
}