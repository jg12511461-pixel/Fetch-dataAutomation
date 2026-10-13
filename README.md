# Fetch Data Automation

## Project Overview

Fetch Data Automation is a Java Spring Boot application designed to fetch data from the GitHub REST API. It retrieves commit information from a GitHub repository and returns the data in a simplified JSON format.

This project demonstrates how to integrate a Spring Boot application with an external REST API.

## Technologies Used

- Java 21
- Spring Boot
- Maven
- GitHub REST API
- RESTful Web Services
- JSON
- IntelliJ IDEA

## Features

- Fetch commit information from a GitHub repository.
- Retrieve commit ID, commit message, author name, author email, and commit date.
- Expose a REST API endpoint to access GitHub commit data.
- Return data in JSON format.

## API Endpoint

**Fetch GitHub commits**

```http
GET /github/commits
```

The endpoint retrieves commit details from the configured GitHub repository.

## Prerequisites

- Java Development Kit (JDK) 21
- Maven
- IntelliJ IDEA or another Java IDE
- Internet connection

## How to Run the Project

1. Clone the repository:

   ```bash
   git clone https://github.com/jg12511461-pixel/Fetch-dataAutomation.git
   ```

2. Open the project in IntelliJ IDEA.

3. Ensure Java 21 is configured.

4. Build the project using Maven:

   ```bash
   mvn clean install
   ```

5. Run the Spring Boot application from your IDE, or use:

   ```bash
   mvn spring-boot:run
   ```

6. Open the API endpoint in your browser or API testing tool:

   ```http
   http://localhost:8080/github/commits
   ```

## Project Purpose

This project was developed to learn and practice Spring Boot, REST API integration, GitHub API data retrieval, and JSON response handling.

## Author

Jyoti Gupta

GitHub: [jg12511461-pixel](https://github.com/jg12511461-pixel)

## Configuration

Before running the application, update the GitHub repository details to fetch data from your own repository.

### 1. Configure the GitHub Repository

Open the Java class where the GitHub API URL is defined. Find the existing URL:

```java
https://api.github.com/repos/jg12511461-pixel/mpp-task-manager/commits
```

Replace `jg12511461-pixel` with your GitHub username and `mpp-task-manager` with the repository name you want to access.

For example:

```java
https://api.github.com/repos/YOUR_USERNAME/YOUR_REPOSITORY/commits
```

Replace `YOUR_USERNAME` and `YOUR_REPOSITORY` with your actual GitHub username and repository name.

### 2. Configure the Application Port

The default Spring Boot port is `8080`.

If port `8080` is already in use, update `server.port` in `src/main/resources/application.properties`:

```properties
server.port=8081
```

You can then access the application at `http://localhost:8081`.

### 3. Run the Application

Make sure JDK 21 and Maven are installed, then run:

```bash
mvn clean install
mvn spring-boot:run
```

### 4. Test the API

Open the following URL in your browser or Postman:

```http
http://localhost:8080/github/commits
```

The API returns commit information from the configured GitHub repository.

### Notes

- The GitHub repository must be public for unauthenticated API access, or you must configure authentication for private repositories.
- GitHub API rate limits apply to unauthenticated requests.
- If you change the application port, use that port in the API URL.
