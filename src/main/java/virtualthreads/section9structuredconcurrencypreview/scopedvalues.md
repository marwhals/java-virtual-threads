Notes on scoped values and structured concurrency

- Scoped values - Values that can be safely and efficiently
shared with methods without using method parameters.
They are preferred to thread-local variables, especially when using large numbers of virtual threads.

Thread Local - introduced in Java 1.2
- Built on top of customised hashmap
- -- Key: Current thread
- -- Value: Any object

- Some objects are NOT thread safe and expensive to create
- --- ex: ObjectMapper
- synchronized might affect performance as threads as will have to wait

Thread Local
- Design flaws
- --- It is mutable
- --- Object in ThreadLocal can live forever even if it is not used
- ------- Fixed thread pool - 500
- --- Child Thread Inheritance is expensive
- Should be used with static final - but people misuse
- Some objects are NOT thread safe and expensive to create
- ----- ex: ObjectMapper (in the past)
- synchronised might affect performance as threads will have to wait

Summary Notes
- ThreadLocal does work with virtual threads
- Scoped values
- ---- Values that can be safely and efficiently shared to methods without using method parameters
- ---- They are preferred to thread-local variable, especially when using large numbers of virtual threads
- Structured concurrency / Structured Task Scope
- ---- To tread a group of tasks running in different threads as a single unit of work
- ---- To automatically propagate the scoped values of current thread to child threads
- -------- Success/Failure - Let the subtasks complete. Status could be either success or failure
- -------- All the subtasks should succeed. If any subtask fails, cancel other running subtasks
- -------- Get the very first success response. Cancel others