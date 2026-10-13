package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.GitHubIssue;

public interface GitHubIssueRepository
        extends JpaRepository<GitHubIssue, Long> {
}