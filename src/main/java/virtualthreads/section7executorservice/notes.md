# Executor Service

- High level concurrency frameworks
- -- Thread management 
- -- Tasks handling
- -- Does thread pooling
- -- Virtual threads are cheap
- -- A task can be divided into multiple smaller subtasks
- -- Executor is a Functional Interface
- ----ExecutorService is an interface which extends Executor
- -- Executor - Is a utility class with factory methods to create an instance of ExecutorService impl
- -- Virtual threads are NOT supposed to be pooled
- ---- Tasks
- ---- Virtual Thread is intended to be created on demand and discard once the task is done
- -- ExecutorService with Virtual threads - Thread per Task creation management
- ---- Don't pool Virtual Threads 
- ---- Thread pool is a group of pre-constructed platform threads that are reused when they become avaiable.
- ---- Some thread pools have a fixed number of threads while others create new threads as needed
- ---- Don't pool virtual threads. Create one for evey application task. virtual threads are short-lived and have shallow call stacks. They don't need the additional overhead or the functionality of thread pools.

Executor Service
- Fixed Thread pool - A thread pool with a dedicated number of threads
- Single Thread Executor - Same as fixed above with a single worker thread. Not configurable, used to execute tasks sequentially.
- Cached thread pool - Create new thread on demand. Reuse existing thread if available. Idle thread life time is 1 minute
- Scheduled Thread Pool - Thread pool which can be used to run tasks as regular intervals
- Thread Per Task Executor - Creates new thread per task on demand ---- Java 21

------- Summary -------
Virtual Threads
- Good for IO tasks to achieve non-blocking benefits "behind the scenes"
- They are not ideal for CPU intensive tasks
- Follow a thread per task
- They should not be pooled/re-used

Executor Service
- A "framework" for high level concurrency
- Submit a task and receive a future
- For a Virtual Thread - we have a thread-per-task executor


ExecutorService with Platform Threads
- single / fixed / cached / scheduled / fork-join pool
- These implementations "pool" threads
- Usage of virtual thread factory is advised against

ExecutorService with Virtual Threads
- single / fixed -> use semaphore + Queue
- cached -> more or less same as thread-per-task
- scheduled -> use platform thread to schedule and virtual threads to execute (finer control)
- Fork join pool is not applicable to virtual threads.....(check / find better definition)