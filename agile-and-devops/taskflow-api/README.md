# TaskFlow API

![CI](https://github.com/El-Gibbor/amalitech-labs/actions/workflows/taskflow-api-ci.yml/badge.svg)

A small Spring Boot REST API for tracking the lifecycle of tasks, built as part of an individual Agile and DevOps assessment. See [../docs](../docs) for the backlog, sprint plans, and sprint reviews and retrospectives.

## Requirements

- Java 11
- Maven (a local install works, or use `mvnw` if you add a wrapper)

## Running the application

```
mvn spring-boot:run
```

The application starts on port 8080 and uses an in memory H2 database, so no external database setup is needed.

## Running the tests

```
mvn test
```

This runs the full suite: unit tests for TaskService and integration tests for the controller and the health endpoint. Test results are also written to target/surefire-reports.

## API

| Method | Path | Description |
|--------|------|-------------|
| POST | /api/tasks | Create a task. Requires a title and a priority. |
| GET | /api/tasks | List all tasks. |
| GET | /api/tasks/{id} | Get a single task by id, or 404 if it does not exist. |
| PATCH | /api/tasks/{id}/status | Update a task's status to TODO, IN_PROGRESS, or DONE. |
| GET | /actuator/health | Health check, returns status UP when the service is running. |

## Continuous integration

Every push and pull request touching this project runs the test suite through GitHub Actions, defined in ../../.github/workflows/taskflow-api-ci.yml.
