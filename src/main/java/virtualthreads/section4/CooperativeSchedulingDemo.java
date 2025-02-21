package virtualthreads.section4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import virtualthreads.util.CommonUtils;

import java.time.Duration;

/*
- Platform Threads are scheduled by the OS Scheduler
- Virtual Threads are scheduled by JVM
-- Dedicated ForkJoinPool schedules Virtual Threads
-- Core pool size is the available pool size
-- Carrier threads will not be blocked during I/O
-- Max pool size is 256

---- Scheduling ---- two types

- Preemptive
-- CPU is allocated for a limited time
-- OS can forcibly pause a running thread to give CPU control to another thread
-- Based on thread-priority, time-slice, availability of ready-to-run threads etc
-- Platform threads can have priorities -- "thread.setPriority(6)"
---- 1 is low priority
---- 10 is high priority
---- 5 is default
-----NOTE
----- Preemptive scheduling behaviour is platform dependent
----- Virtual threads have default priority and this cannot be modified.

- Cooperative
-- CPU is allocated till the execution is completed
---- Or Thread should be willing to give CPU to another thread. (Thread.yield())
-- Execution is not interrupted or forcibly paused
-- If there is a long-running thread/task, other threads might have to starve.

 */

public class CooperativeSchedulingDemo {
    private static final Logger log = LoggerFactory.getLogger(CooperativeSchedulingDemo.class);

    static {
        System.setProperty("jdk.virtualThreadScheduler.parallelism", "1");
        System.setProperty("jdk.virtualThreadScheduler.maxPoolSize", "1");
    }

    public static void main(String[] args) {

        var builder = Thread.ofVirtual();
        var t1 = builder.unstarted(() -> demo(1));
        var t2 = builder.unstarted(() -> demo(2));
        var t3 = builder.unstarted(() -> demo(3));
        t1.start();
        t2.start();
        t3.start();
        CommonUtils.sleep(Duration.ofSeconds(2));

    }

    private static void demo(int threadNumber){
        log.info("thread-{} started", threadNumber);
        for (int i = 0; i < 10; i++) {
            log.info("thread-{} is printing {}. Thread: {}", threadNumber, i, Thread.currentThread());
            Thread.yield();
        }
        log.info("thread-{} ended", threadNumber);
    }
}
