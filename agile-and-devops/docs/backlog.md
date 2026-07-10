# TaskFlow Product Backlog

## Product Vision

TaskFlow provides individuals and small teams with a simple, reliable API to track the lifecycle of their tasks, from creation through completion, without the overhead of a full project management tool.

## Backlog

Stories are prioritized using the MoSCoW method and estimated in story points using a Fibonacci-like scale (1, 2, 3, 5, 8). Priority order below reflects delivery sequence, highest priority first.

| # | User Story | Priority | Estimate |
|---|-------------|----------|----------|
| 1 | As a user, I want to create a task with a title, description, and priority, so that I can record work that needs to be done. | Must have | 3 |
| 2 | As a user, I want to view a list of all my tasks, so that I can see everything I need to work on. | Must have | 2 |
| 3 | As a user, I want to retrieve a single task by its id, so that I can view its full details. | Must have | 1 |
| 4 | As a user, I want to update the status of a task (TODO, IN_PROGRESS, DONE), so that I can track its progress. | Must have | 3 |
| 5 | As a user, I want to delete a task, so that I can remove items that are no longer relevant. | Should have | 2 |
| 6 | As a user, I want to filter tasks by status, so that I can focus on tasks that are still outstanding. | Should have | 3 |

Total backlog: 6 stories, 14 story points.

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

**5. Delete a task**
- Given a task exists, when a delete request is submitted for its id, then the task is removed and subsequent retrieval of that id returns a not-found response.
- Given no task exists with a given id, when a delete request is submitted, then the API returns a not-found response.

**6. Filter tasks by status**
- Given tasks exist with varying statuses, when a filter by a specific status is requested, then the API returns only tasks matching that status.
- Given no tasks match the requested status, when the filter is applied, then the API returns an empty list.
