package virtualthreads.section5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import virtualthreads.util.CommonUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* ReentrantLock
 * - Dealing with race conditions - A thread which has been waiting longer will get a chance to acquire the lock
 * - Has a time-out - the amount of time a thread will wait to acquire the lock
 */

public class ReentrantLockDemo {

    private static final Logger log = LoggerFactory.getLogger(ReentrantLockDemo.class);
    private static final Lock lock = new ReentrantLock();
    private static final List<Integer> list = new ArrayList<>();

    public static void main(String[] args) {

        demo(Thread.ofVirtual());

        CommonUtils.sleep(Duration.ofSeconds(2));

        log.info("list size: {}", list.size());
    }

    private static void demo(Thread.Builder builder) {
        for (int i = 0; i < 50; i++) {
            builder.start(() -> {
                log.info("Task started. {}", Thread.currentThread());
                for (int j = 0; j < 200; j++) {
                    inMemoryTask();
                }
                log.info("Task ended. {}", Thread.currentThread());
            });
        }
    }

    private static void inMemoryTask() {
        try {
            lock.lock();
            list.add(1);
        } catch (Exception e) {
            log.error("error", e);
        } finally {
            lock.unlock();
        }
    }

}
