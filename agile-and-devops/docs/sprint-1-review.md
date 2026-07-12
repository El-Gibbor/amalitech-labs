# Sprint 1 Review

## Goal

My goal for this sprint was to deliver the core read and write capability of the TaskFlow API and to stand up a CI pipeline with automated tests behind it.

## What I delivered

I completed all four must have stories from the refined backlog, and each one is covered by unit tests, integration tests, or both.

| # | Story | Evidence |
|---|-------|----------|
| 1 | Create a task | POST to /api/tasks validates title and priority, and defaults the status to TODO |
| 2 | List all tasks | GET to /api/tasks returns every task currently stored |
| 3 | Retrieve a task by id | GET to /api/tasks/{id} returns the task, or a 404 if it does not exist |
| 4 | Update task status | PATCH to /api/tasks/{id}/status validates the new status and rejects invalid values without changing the task |

Beyond the original Sprint 1 plan, I also:

1. Refined the backlog from six stories down to four must have stories, so the scope stayed achievable.
2. Set up a GitHub Actions CI workflow at .github/workflows/taskflow-api-ci.yml that runs mvn test on every push and pull request touching agile-and-devops/taskflow-api.

## Demo

I verified the following behavior by hand and through automated tests.

1. Posting a valid task to /api/tasks returns a 201 response with a generated id and a status of TODO.
2. Posting a task with a blank title returns a 400 response with a validation error explaining why.
3. Getting /api/tasks returns an array containing every task I created.
4. Getting /api/tasks/{id} for an id that does not exist returns a 404 response.
5. Sending a PATCH to /api/tasks/{id}/status with a status of IN_PROGRESS returns a 200 response with the updated status.
6. Sending a PATCH to /api/tasks/{id}/status with an invalid value like BOGUS returns a 400 response, and a follow up GET confirms the task's status was not changed.

## Test evidence

I have 16 automated tests, all passing when I run mvn test. That is 9 integration tests in TaskControllerIntegrationTest and 7 unit tests in TaskServiceTest.

## Commit history

By the end of the sprint I was committing in small, single purpose pieces rather than one commit per story: a domain class or DTO first, then the service method, then the controller endpoint, then any error handling, then the tests. The full history is visible in git log from commit 819598c through 36ee477.
