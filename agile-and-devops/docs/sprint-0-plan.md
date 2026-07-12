# Sprint 0 – Planning

## Product Vision

TaskFlow provides individuals and small teams with a simple, reliable API to track the lifecycle of their tasks, from creation through completion, without the overhead of a full project management tool.

## Definition of Done

A backlog item is considered done when all of the following conditions are satisfied:

1. The implementation compiles and the CI pipeline passes without errors.
2. Unit and, where applicable, integration tests exist for the story and pass locally and in CI.
3. The behavior matches the story's acceptance criteria, verified manually or through automated tests.
4. Input is validated at the API boundary and invalid requests are rejected with an appropriate error response.
5. The change is committed to version control with a descriptive message describing the increment delivered.
6. Relevant logging is present for the operation (from Sprint 2 onward, per the monitoring requirement).
7. Documentation (backlog, README, or sprint artifacts) is updated to reflect the change where relevant.

## Sprint 1 Plan

**Goal:** Deliver the core read and create capability of the task API, establish the project skeleton, and stand up the CI pipeline with automated tests.

**Selected stories:**

| # | Story | Estimate |
|---|-------|----------|
| 1 | Create a task | 3 |
| 2 | List all tasks | 2 |
| 3 | Retrieve a single task by id | 1 |
| 4 | Update task status | 3 |

**Sprint 1 capacity:** 9 story points.

Note: the backlog was refined mid-Sprint-1 to 4 must-have stories (delete and filter-by-status cut). With reduced scope, all 4 remaining stories were delivered within Sprint 1 instead of the original 3, leaving story time for the CI pipeline as well. See the Sprint 1 Review for details.

## Remaining Backlog (Sprint 2)

| # | Story | Estimate |
|---|-------|----------|
| 5 | Health check endpoint | 1 |
| 6 | Log key task operations | 2 |

Stories 5 and 6 were added during Sprint 2 refinement to satisfy the monitoring/logging requirement as genuine backlog items, since the original backlog was fully delivered in Sprint 1.
