# Structured concurrency
- Goal
- - Introduce new API to tread a group of related tasks running in different threads as single unit of work

Use Cases
- A task is divided into mulltiple smaller subtasks that run in different threads
 -- Success/Faulure - Let the subtasks cimplete. Status could be either success or failure
 -- (Similar to what we did using Executor Service)
- All Success - All the tasks should succeed. If any subtasks fails, cancel other running subtasks
- First success - Get the very first success response. Cancel others.