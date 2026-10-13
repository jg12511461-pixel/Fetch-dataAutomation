package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.GitHubCommitFile;

public interface GitHubCommitFileRepository
        extends JpaRepository<GitHubCommitFile, Long> {
}