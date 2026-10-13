package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.GitHubUser;

public interface GitHubUserRepository
        extends JpaRepository<GitHubUser, Long> {
}