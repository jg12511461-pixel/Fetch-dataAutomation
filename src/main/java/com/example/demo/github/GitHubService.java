package com.example.demo.github;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.entity.GitHubBranch;
import com.example.demo.entity.GitHubCommit;
import com.example.demo.entity.GitHubCommitFile;
import com.example.demo.entity.GitHubEvent;
import com.example.demo.entity.GitHubIssue;
import com.example.demo.entity.GitHubIssueComment;
import com.example.demo.entity.GitHubPullRequest;
import com.example.demo.entity.GitHubPullRequestCommit;
import com.example.demo.entity.GitHubPullRequestReview;
import com.example.demo.entity.GitHubRepository;
import com.example.demo.entity.GitHubUser;

import com.example.demo.repository.GitHubBranchRepository;
import com.example.demo.repository.GitHubCommitFileRepository;
import com.example.demo.repository.GitHubCommitRepository;
import com.example.demo.repository.GitHubEventRepository;
import com.example.demo.repository.GitHubIssueCommentRepository;
import com.example.demo.repository.GitHubIssueRepository;
import com.example.demo.repository.GitHubPullRequestCommitRepository;
import com.example.demo.repository.GitHubPullRequestRepository;
import com.example.demo.repository.GitHubPullRequestReviewRepository;
import com.example.demo.repository.GitHubRepositoryRepository;
import com.example.demo.repository.GitHubUserRepository;

@Service
public class GitHubService {

    private final GitHubApiClient apiClient;

    private final GitHubRepositoryRepository repositoryRepository;
    private final GitHubBranchRepository branchRepository;
    private final GitHubUserRepository userRepository;
    private final GitHubCommitRepository commitRepository;
    private final GitHubCommitFileRepository commitFileRepository;
    private final GitHubPullRequestRepository pullRequestRepository;
    private final GitHubPullRequestCommitRepository pullRequestCommitRepository;
    private final GitHubPullRequestReviewRepository pullRequestReviewRepository;
    private final GitHubIssueRepository issueRepository;
    private final GitHubIssueCommentRepository issueCommentRepository;
    private final GitHubEventRepository eventRepository;

    public GitHubService(
            GitHubApiClient apiClient,
            GitHubRepositoryRepository repositoryRepository,
            GitHubBranchRepository branchRepository,
            GitHubUserRepository userRepository,
            GitHubCommitRepository commitRepository,
            GitHubCommitFileRepository commitFileRepository,
            GitHubPullRequestRepository pullRequestRepository,
            GitHubPullRequestCommitRepository pullRequestCommitRepository,
            GitHubPullRequestReviewRepository pullRequestReviewRepository,
            GitHubIssueRepository issueRepository,
            GitHubIssueCommentRepository issueCommentRepository,
            GitHubEventRepository eventRepository) {

        this.apiClient = apiClient;
        this.repositoryRepository = repositoryRepository;
        this.branchRepository = branchRepository;
        this.userRepository = userRepository;
        this.commitRepository = commitRepository;
        this.commitFileRepository = commitFileRepository;
        this.pullRequestRepository = pullRequestRepository;
        this.pullRequestCommitRepository = pullRequestCommitRepository;
        this.pullRequestReviewRepository = pullRequestReviewRepository;
        this.issueRepository = issueRepository;
        this.issueCommentRepository = issueCommentRepository;
        this.eventRepository = eventRepository;
    }

    public void syncAll() {

        syncRepository();
        syncBranches();
        syncUsers();
        syncCommits();
        syncPullRequests();
        syncIssues();
        syncIssueComments();
        syncEvents();
    }

    private void syncRepository() {

        Map<String, Object> data = apiClient.getRepository();

        if (data == null) {
            return;
        }

        GitHubRepository repository = new GitHubRepository();

        repository.setGithubId(
                String.valueOf(data.get("id")));

        repository.setName(
                (String) data.get("name"));

        repository.setFullName(
                (String) data.get("full_name"));

        repository.setDescription(
                (String) data.get("description"));

        repository.setDefaultBranch(
                (String) data.get("default_branch"));

        repository.setLanguage(
                (String) data.get("language"));

        repository.setVisibility(
                (String) data.get("visibility"));

        Map<String, Object> owner =
                (Map<String, Object>) data.get("owner");

        if (owner != null) {
            repository.setOwner(
                    (String) owner.get("login"));
        }

        repositoryRepository.save(repository);
    }

    private void syncBranches() {

        List<Map<String, Object>> data =
                apiClient.getBranches();

        if (data == null) {
            return;
        }

        for (Map<String, Object> item : data) {

            GitHubBranch branch = new GitHubBranch();

            branch.setBranchName(
                    (String) item.get("name"));

            branch.setProtectedBranch(
                    (Boolean) item.get("protected"));

            Map<String, Object> commit =
                    (Map<String, Object>) item.get("commit");

            if (commit != null) {
                branch.setCommitSha(
                        (String) commit.get("sha"));
            }

            branchRepository.save(branch);
        }
    }

    private void syncUsers() {

        List<Map<String, Object>> data =
                apiClient.getContributors();

        if (data == null) {
            return;
        }

        for (Map<String, Object> item : data) {

            GitHubUser user = new GitHubUser();

            user.setGithubId(
                    String.valueOf(item.get("id")));

            user.setUsername(
                    (String) item.get("login"));

            user.setProfileUrl(
                    (String) item.get("html_url"));

            userRepository.save(user);
        }
    }

    private void syncCommits() {

        List<Map<String, Object>> data =
                apiClient.getCommits();

        if (data == null) {
            return;
        }

        for (Map<String, Object> item : data) {

            GitHubCommit commit = new GitHubCommit();

            String sha =
                    (String) item.get("sha");

            commit.setSha(sha);

            Map<String, Object> commitData =
                    (Map<String, Object>) item.get("commit");

            if (commitData != null) {

                commit.setMessage(
                        (String) commitData.get("message"));

                Map<String, Object> author =
                        (Map<String, Object>)
                                commitData.get("author");

                if (author != null) {

                    commit.setAuthorName(
                            (String) author.get("name"));

                    commit.setAuthorEmail(
                            (String) author.get("email"));

                    commit.setCommitDate(
                            (String) author.get("date"));
                }
            }

            Map<String, Object> githubAuthor =
                    (Map<String, Object>) item.get("author");

            if (githubAuthor != null) {

                commit.setUsername(
                        (String) githubAuthor.get("login"));
            }

            commitRepository.save(commit);

            syncCommitFiles(sha);
        }
    }

    private void syncCommitFiles(String sha) {

        Map<String, Object> data =
                apiClient.getCommit(sha);

        if (data == null) {
            return;
        }

        List<Map<String, Object>> files =
                (List<Map<String, Object>>) data.get("files");

        if (files == null) {
            return;
        }

        for (Map<String, Object> file : files) {

            GitHubCommitFile commitFile =
                    new GitHubCommitFile();

            commitFile.setCommitSha(sha);

            commitFile.setFileName(
                    (String) file.get("filename"));

            commitFile.setStatus(
                    (String) file.get("status"));

            commitFile.setAdditions(
                    ((Number) file.get("additions")).intValue());

            commitFile.setDeletions(
                    ((Number) file.get("deletions")).intValue());

            commitFile.setChanges(
                    ((Number) file.get("changes")).intValue());

            commitFileRepository.save(commitFile);
        }
    }

    private void syncPullRequests() {

        List<Map<String, Object>> data =
                apiClient.getPullRequests();

        if (data == null) {
            return;
        }

        for (Map<String, Object> item : data) {

            GitHubPullRequest pullRequest =
                    new GitHubPullRequest();

            Integer number =
                    ((Number) item.get("number")).intValue();

            pullRequest.setPullNumber(number);

            pullRequest.setTitle(
                    (String) item.get("title"));

            pullRequest.setState(
                    (String) item.get("state"));

            pullRequest.setCreatedAt(
                    (String) item.get("created_at"));

            pullRequest.setUpdatedAt(
                    (String) item.get("updated_at"));

            pullRequest.setClosedAt(
                    (String) item.get("closed_at"));

            pullRequest.setMergedAt(
                    (String) item.get("merged_at"));

            Map<String, Object> user =
                    (Map<String, Object>) item.get("user");

            if (user != null) {
                pullRequest.setUsername(
                        (String) user.get("login"));
            }

            Map<String, Object> head =
                    (Map<String, Object>) item.get("head");

            if (head != null) {
                pullRequest.setHeadBranch(
                        (String) head.get("ref"));
            }

            Map<String, Object> base =
                    (Map<String, Object>) item.get("base");

            if (base != null) {
                pullRequest.setBaseBranch(
                        (String) base.get("ref"));
            }

            pullRequestRepository.save(pullRequest);

            syncPullRequestCommits(number);
            syncPullRequestReviews(number);
        }
    }

    private void syncPullRequestCommits(Integer pullNumber) {

        List<Map<String, Object>> data =
                apiClient.getPullRequestCommits(pullNumber);

        if (data == null) {
            return;
        }

        for (Map<String, Object> item : data) {

            GitHubPullRequestCommit commit =
                    new GitHubPullRequestCommit();

            commit.setPullNumber(pullNumber);

            commit.setCommitSha(
                    (String) item.get("sha"));

            Map<String, Object> author =
                    (Map<String, Object>) item.get("author");

            if (author != null) {
                commit.setUsername(
                        (String) author.get("login"));
            }

            Map<String, Object> commitData =
                    (Map<String, Object>) item.get("commit");

            if (commitData != null) {

                Map<String, Object> authorData =
                        (Map<String, Object>)
                                commitData.get("author");

                if (authorData != null) {
                    commit.setCommitDate(
                            (String) authorData.get("date"));
                }
            }

            pullRequestCommitRepository.save(commit);
        }
    }

    private void syncPullRequestReviews(Integer pullNumber) {

        List<Map<String, Object>> data =
                apiClient.getPullRequestReviews(pullNumber);

        if (data == null) {
            return;
        }

        for (Map<String, Object> item : data) {

            GitHubPullRequestReview review =
                    new GitHubPullRequestReview();

            review.setPullNumber(pullNumber);

            review.setReviewId(
                    ((Number) item.get("id")).longValue());

            review.setState(
                    (String) item.get("state"));

            review.setSubmittedAt(
                    (String) item.get("submitted_at"));

            Map<String, Object> user =
                    (Map<String, Object>) item.get("user");

            if (user != null) {
                review.setUsername(
                        (String) user.get("login"));
            }

            pullRequestReviewRepository.save(review);
        }
    }

    private void syncIssues() {

        List<Map<String, Object>> data =
                apiClient.getIssues();

        if (data == null) {
            return;
        }

        for (Map<String, Object> item : data) {

            // GitHub Issues API can also return Pull Requests.
            if (item.containsKey("pull_request")) {
                continue;
            }

            GitHubIssue issue = new GitHubIssue();

            issue.setIssueNumber(
                    ((Number) item.get("number")).intValue());

            issue.setTitle(
                    (String) item.get("title"));

            issue.setState(
                    (String) item.get("state"));

            issue.setCreatedAt(
                    (String) item.get("created_at"));

            issue.setUpdatedAt(
                    (String) item.get("updated_at"));

            issue.setClosedAt(
                    (String) item.get("closed_at"));

            Map<String, Object> user =
                    (Map<String, Object>) item.get("user");

            if (user != null) {
                issue.setUsername(
                        (String) user.get("login"));
            }

            issueRepository.save(issue);
        }
    }

    private void syncIssueComments() {

        List<Map<String, Object>> data =
                apiClient.getIssueComments();

        if (data == null) {
            return;
        }

        for (Map<String, Object> item : data) {

            GitHubIssueComment comment =
                    new GitHubIssueComment();

            comment.setCommentId(
                    ((Number) item.get("id")).longValue());

            comment.setBody(
                    (String) item.get("body"));

            comment.setCreatedAt(
                    (String) item.get("created_at"));

            comment.setUpdatedAt(
                    (String) item.get("updated_at"));

            Map<String, Object> user =
                    (Map<String, Object>) item.get("user");

            if (user != null) {
                comment.setUsername(
                        (String) user.get("login"));
            }

            Map<String, Object> issue =
                    (Map<String, Object>) item.get("issue_url");

            if (issue != null) {
                // Reserved for future issue URL mapping.
            }

            issueCommentRepository.save(comment);
        }
    }

    private void syncEvents() {

        List<Map<String, Object>> data =
                apiClient.getEvents();

        if (data == null) {
            return;
        }

        for (Map<String, Object> item : data) {

            GitHubEvent event = new GitHubEvent();

            event.setEventId(
                    String.valueOf(item.get("id")));

            event.setEventType(
                    (String) item.get("type"));

            event.setCreatedAt(
                    (String) item.get("created_at"));

            Map<String, Object> actor =
                    (Map<String, Object>) item.get("actor");

            if (actor != null) {
                event.setUsername(
                        (String) actor.get("login"));
            }

            eventRepository.save(event);
        }
    }
}