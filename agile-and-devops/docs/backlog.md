# TaskFlow Product Backlog

TaskFlow provides individuals and small teams with a simple, reliable API to track the lifecycle of their tasks, from creation through completion, without the overhead of a full project management tool.

## Backlog

Stories are prioritized using the MoSCoW method and estimated in story points using a Fibonacci-like scale (1, 2, 3, 5, 8). Priority order below reflects delivery sequence, highest priority first.

| # | User Story | Priority | Estimate |
|---|-------------|----------|----------|
| 1 | As a user, I want to create a task with a title, description, and priority, so that I can record work that needs to be done. | Must have | 3 |
| 2 | As a user, I want to view a list of all my tasks, so that I can see everything I need to work on. | Must have | 2 |
| 3 | As a user, I want to retrieve a single task by its id, so that I can view its full details. | Must have | 1 |
| 4 | As a user, I want to update the status of a task (TODO, IN_PROGRESS, DONE), so that I can track its progress. | Must have | 3 |

Total backlog: 4 stories, 9 story points.

Delete and filter-by-status were cut during backlog refinement to keep scope achievable within two sprints; may be picked up later if time allows.

## Acceptance Criteria

**1. Create a task**
- Given valid title, description, and priority, when a task is submitted, then the API returns the created task with a generated id and a default status of `TODO`.
- Given a blank or missing title, when a task is submitted, then the API rejects the request with a validation error and no task is created.

**2. List all tasks**
- Given any number of existing tasks, when the task list is requested, then the API returns all tasks currently stored.
- Given no tasks exist, when the task list is requested, then the API returns an empty list rather than an error.

**3. Retrieve a single task by id**
- Given a task exists with a given id, when that id is requested, then the API returns the full details of that task.
- Given no task exists with a given id, when that id is requested, then the API returns a not-found response.

**4. Update task status**
- Given a task exists, when its status is updated to a valid value (`TODO`, `IN_PROGRESS`, `DONE`), then the API returns the task with the updated status.
- Given an invalid status value is submitted, when the update is requested, then the API rejects the request and the task's status remains unchanged.
