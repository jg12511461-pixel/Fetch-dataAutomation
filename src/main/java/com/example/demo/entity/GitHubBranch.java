package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "github_branches")
public class GitHubBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String branchName;
    private String commitSha;
    private Boolean protectedBranch;

    public GitHubBranch() {
    }

    public Long getId() {
        return id;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCommitSha() {
        return commitSha;
    }

    public void setCommitSha(String commitSha) {
        this.commitSha = commitSha;
    }

    public Boolean getProtectedBranch() {
        return protectedBranch;
    }

    public void setProtectedBranch(Boolean protectedBranch) {
        this.protectedBranch = protectedBranch;
    }
}