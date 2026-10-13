package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.GitHubPullRequest;

public interface GitHubPullRequestRepository
        extends JpaRepository<GitHubPullRequest, Long> {
}