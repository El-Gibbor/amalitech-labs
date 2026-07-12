# Sprint 1 Retrospective

## What went well

I trimmed the backlog from six stories down to four must have stories early in the sprint, once I realized the original scope was too much for me to carry through two sprints. I ended up delivering all four stories instead of the three I had originally planned for Sprint 1, and I still had room left to set up the CI pipeline.

I also noticed that once I had a consistent pattern in place (a DTO for the request body, validation annotations, a shared ApiExceptionHandler for error responses, and MockMvc for integration tests) each new story took less time to build than the one before it. Story four (update task status) reused almost the same shape as story three (get task by id), just with a new DTO and a new exception handler case.

By the last story I was also committing in much smaller pieces. Instead of one commit per story, I split story four into five separate commits: the DTO, the service method, the controller endpoint, the error handling fix, and the tests. That made the history much easier to read and much easier to check one change at a time.

## What did not go well

For the first two stories I committed the whole thing at once, controller, service, and tests together. That was too coarse. It did not really show the kind of incremental progress that should be demonstrated, and I only caught this and fixed it partway through the sprint.

I also did not set up the CI pipeline until after all four stories were already implemented, and most of the actual development work in Sprint 1 was not verified by CI as I was writing it. I only wired it up at the end, so it verified the finished result rather than catching problems along the way.

Until partway through the sprint, I didn't have Maven installed locally. So I was not actually running my tests for the first two stories while I was writing them. I trusted that the code was correct instead of proving it, until I fixed the tooling gap.

## Improvements for Sprint 2

1. Set up the CI pipeline before I write any story code, not after, so every commit in Sprint 2 gets verified by the pipeline as it lands instead of being checked retroactively.

2. Keep committing in small, single purpose pieces from the very start of the sprint, the way I did for story four, rather than falling back into one commit per story.

3. Always Confirm my local build and test setup works before I start implementing anything, so I can actually run tests as I go instead of discovering a tooling gap midway through.
