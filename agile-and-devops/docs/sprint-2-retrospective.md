# Sprint 2 Retrospective

## What went well

I actually followed through on all three improvements I wrote down at the end of Sprint 1. The CI pipeline was already running before I wrote a single line of Sprint 2 code, so every commit in this sprint was covered by CI from the moment it existed rather than after the fact. I kept committing in small, single purpose pieces the whole way through, dependency first, then test, then service change, then error handling change, instead of sliding back into one commit per story. And I confirmed my build worked with a plain compile before I even started writing the logging code, so I was not caught off guard by a tooling gap again.

Choosing Actuator for the health endpoint instead of writing my own controller was a good call. It meant story 5 was really just one dependency and one test, which left me more time to make the logging in story 6 genuinely useful instead of rushed.

Running the application myself and reading the console output for the demo was worth doing. It is one thing to have a test pass and another thing to actually watch the log line appear when I hit the endpoint, and doing that caught nothing wrong this time, but it gave me real confidence in the feature instead of assumed confidence.

## What did not go well

I still do not have an automated test that checks the logging itself. Everything I know about the logging working is from watching the console by hand during the demo, not from something that will keep verifying it every time CI runs. If someone changed the log level or removed a log line by accident later, nothing in the test suite would catch it.

I also noticed that by the time I got to story 6, I was moving fast enough that I did not stop to consider whether task descriptions or other fields should be included in the log lines, or whether logging the full title could be a problem if a task ever contained sensitive text. I kept the log lines minimal, id and status only where possible, but that was more instinct than a decision I actually stopped and made deliberately.

## Lessons learned

The backlog trimming decision from early in Sprint 1 turned out to be the single most important thing I did on this project. Every other improvement, the smaller commits, the CI first approach, the manual verification habit, only had room to happen because the scope stayed small enough for me to actually think about how I was working instead of just rushing to finish stories.

Adding libraries like Actuator instead of writing everything by hand is not cutting corners, it is a real DevOps skill. Knowing when a well tested, widely used dependency solves my problem better than custom code freed up time and attention for the parts of the work that actually needed my judgment, like what to log and where.

If I did another sprint after this one, I would treat writing a test before implementing the behavior, rather than after, as the next habit to build, the same way I built the small commit habit and the CI first habit during this project.
