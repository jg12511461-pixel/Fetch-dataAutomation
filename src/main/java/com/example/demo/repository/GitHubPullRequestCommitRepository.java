package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.GitHubPullRequestCommit;

public interface GitHubPullRequestCommitRepository
        extends JpaRepository<GitHubPullRequestCommit, Long> {
}