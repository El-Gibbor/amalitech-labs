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

Below is a real transcript from running the application locally and hitting each endpoint with curl, followed by the matching application log lines.

```
$ curl -s -X POST http://localhost:8080/api/tasks -H "Content-Type: application/json" -d '{"title":"Write Sprint 1 review","description":"Summarize delivered stories","priority":"HIGH"}'
{"id":1,"title":"Write Sprint 1 review","description":"Summarize delivered stories","priority":"HIGH","status":"TODO"}

$ curl -s -X POST http://localhost:8080/api/tasks -H "Content-Type: application/json" -d '{"title":""}'
{"message":"Validation failed","details":["title must not be blank","priority must not be null"]}

$ curl -s http://localhost:8080/api/tasks
[{"id":1,"title":"Write Sprint 1 review","description":"Summarize delivered stories","priority":"HIGH","status":"TODO"}]

$ curl -s http://localhost:8080/api/tasks/1
{"id":1,"title":"Write Sprint 1 review","description":"Summarize delivered stories","priority":"HIGH","status":"TODO"}

$ curl -s http://localhost:8080/api/tasks/999
{"message":"Task not found with id 999","details":[]}

$ curl -s -X PATCH http://localhost:8080/api/tasks/1/status -H "Content-Type: application/json" -d '{"status":"IN_PROGRESS"}'
{"id":1,"title":"Write Sprint 1 review","description":"Summarize delivered stories","priority":"HIGH","status":"IN_PROGRESS"}

$ curl -s http://localhost:8080/api/tasks/1
{"id":1,"title":"Write Sprint 1 review","description":"Summarize delivered stories","priority":"HIGH","status":"IN_PROGRESS"}
```

Matching application console output for the same run:

```
2026-07-12 21:05:55.056  INFO 126528 --- [nio-8080-exec-1] com.taskflow.task.TaskService            : Created task id=1 title="Write Sprint 1 review"
2026-07-12 21:05:55.097  WARN 126528 --- [nio-8080-exec-2] com.taskflow.common.ApiExceptionHandler  : Validation failed: [title must not be blank, priority must not be null]
2026-07-12 21:05:55.242  WARN 126528 --- [nio-8080-exec-7] com.taskflow.common.ApiExceptionHandler  : Task not found with id 999
2026-07-12 21:05:55.286  INFO 126528 --- [nio-8080-exec-9] com.taskflow.task.TaskService            : Updated task id=1 status=IN_PROGRESS
```

## Test evidence

I have 16 automated tests, all passing when I run mvn test. That is 9 integration tests in TaskControllerIntegrationTest and 7 unit tests in TaskServiceTest. The current, full test suite results (17 tests, after Sprint 2 added the health check test) are captured in docs/evidence/test-results.txt, and a real passing CI run is captured in docs/evidence/ci-run-12cc514.log.

## Commit history

By the end of the sprint I was committing in small, single purpose pieces rather than one commit per story: a domain class or DTO first, then the service method, then the controller endpoint, then any error handling, then the tests. The full history is visible in git log from commit 819598c through 36ee477.
