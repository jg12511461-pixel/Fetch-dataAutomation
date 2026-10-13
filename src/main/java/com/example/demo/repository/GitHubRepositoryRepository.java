package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.GitHubRepository;

public interface GitHubRepositoryRepository
        extends JpaRepository<GitHubRepository, Long> {
}